package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.quest.QuestType;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum Place {
    CITY("町中",
            new int[]{80, 0, 20, 0},
            new String[]{"警備", "運搬手伝い", "工事手伝い"},
            new String[]{""},
            new String[]{"ワイルドハウンド退治", "ラージラット退治"},
            new String[]{""}),
    MUSHROOM_FOREST("茸の森",
            new int[]{10, 40, 25, 25},
            new String[]{"職人警護", "運搬手伝い"},
            new String[]{"薬草の採集", "食卓キノコの採集", "薬用キノコの採集"},
            new String[]{"暴れラビット退治", "フォレストスライム退治"},
            new String[]{"奥地探索"});

    private final String placeName;
    private final int[] questTypeTable;
    private final String[] taskQuestNames;
    private final String[] harvestQuestNames;
    private final String[] huntQuestNames;
    private final String[] exploreQuestNames;

    public QuestType decideQuestType(Dice dice) {
        int result = dice.roll(1, Arrays.stream(questTypeTable).sum());
        int counter = 0;
        for (int i = 0; i < questTypeTable.length - 1; i++) {
            counter += questTypeTable[i];
            if (result <= counter) {
                return QuestType.values()[i];
            }
        }
        return QuestType.values()[QuestType.values().length - 1];
    }

    public String decideQuestName(QuestType type, Dice dice) {
        String[] options = new String[]{""};
        if (type.equals(QuestType.TASK)) options = taskQuestNames;
        else if (type.equals(QuestType.HARVEST)) options = harvestQuestNames;
        else if (type.equals(QuestType.HUNT)) options = huntQuestNames;
        else if (type.equals(QuestType.EXPLORE)) options = exploreQuestNames;

        int choice = dice.roll(1, options.length);
        return placeName + "の" + options[choice - 1];
    }
}
