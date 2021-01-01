package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.quest.QuestType;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum Place {
    CITY("町",
            new int[]{75, 0, 25, 0},
            new QuestContent[]{QuestContent.CITY_GUARD, QuestContent.CITY_ASSIST_HAUL, QuestContent.CITY_ASSIST_CONSTRUCTION},
            new QuestContent[]{},
            new QuestContent[]{QuestContent.CITY_EXTERMINATE_WILD_HOUNDS, QuestContent.CITY_EXTERMINATE_LARGE_RATS},
            new QuestContent[]{}),
    MUSHROOM_FOREST("茸の森",
            new int[]{10, 40, 25, 25},
            new QuestContent[]{QuestContent.M_FOREST_ESCORT_LUMBERJACK, QuestContent.M_FOREST_ESCORT_ROAD_MAINTENANCE},
            new QuestContent[]{QuestContent.M_FOREST_HARVEST_MEDICINE_MUSHROOM},
            new QuestContent[]{QuestContent.M_FOREST_EXTERMINATE_FOREST_SLIME},
            new QuestContent[]{QuestContent.M_FOREST_EXPLORE});

    private final String placeName;
    private final int[] questTypeTable;
    private final QuestContent[] taskQuestContents;
    private final QuestContent[] harvestQuestContents;
    private final QuestContent[] huntQuestContents;
    private final QuestContent[] exploreQuestContents;

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

    public QuestContent decideQuestDetail(QuestType type, Dice dice) {
        QuestContent[] options = new QuestContent[]{};
        if (type.equals(QuestType.TASK)) options = taskQuestContents;
        else if (type.equals(QuestType.HARVEST)) options = harvestQuestContents;
        else if (type.equals(QuestType.HUNT)) options = huntQuestContents;
        else if (type.equals(QuestType.EXPLORE)) options = exploreQuestContents;

        if (options.length == 0) throw new IllegalStateException();

        int choice = dice.roll(1, options.length);
        return options[choice - 1];
    }
}
