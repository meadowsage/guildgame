package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static com.meadowsage.guildgame.model.quest.QuestContent.CITY_WEEDING;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniqueQuest {
    FIRST(QuestType.TASK, CITY_WEEDING, 10, 0, Place.CITY);

    QuestType type;
    QuestContent questContent;
    int difficulty;
    int danger;
    Place place;

    public Quest getInstance() {
        return new Quest(type, questContent, difficulty, danger, place);
    }
}
