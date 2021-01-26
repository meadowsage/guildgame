package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public enum Place {
    CITY("始まりの町"),
    MUSHROOM_FOREST("茸の森");

    private final String placeName;

    public QuestContent decideQuestContent(Dice dice) {
        List<QuestContent> questContents = QuestContent.valuesAt(this);
        int choice = dice.roll(1, questContents.size());
        return questContents.get(choice - 1);
    }
}
