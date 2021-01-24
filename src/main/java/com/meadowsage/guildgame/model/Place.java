package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum Place {
    CITY("始まりの町", new QuestContent[]{
            QuestContent.CITY_GUARD,
            QuestContent.CITY_ASSIST_HAUL,
            QuestContent.CITY_ASSIST_CONSTRUCTION,
            QuestContent.CITY_EXTERMINATE_WILD_HOUNDS,
            QuestContent.CITY_EXTERMINATE_LARGE_RATS}
    ),
    MUSHROOM_FOREST("茸の森", new QuestContent[]{
            QuestContent.M_FOREST_ESCORT_LUMBERJACK,
            QuestContent.M_FOREST_ESCORT_ROAD_MAINTENANCE,
            QuestContent.M_FOREST_HARVEST_MEDICINE_MUSHROOM,
            QuestContent.M_FOREST_EXTERMINATE_FOREST_SLIME
    });

    private final String placeName;
    private final QuestContent[] questContents;

    public QuestContent decideQuestContent(Dice dice) {
        int choice = dice.roll(1, questContents.length);
        return questContents[choice - 1];
    }

    // FIXME この構造で良いのだろうか…
    public static Place findPlaceOf(QuestContent questContent){
        return Arrays.stream(values())
                .filter(place -> Arrays.asList(place.questContents).contains(questContent))
                .findAny().orElseThrow(IllegalStateException::new);
    }
}
