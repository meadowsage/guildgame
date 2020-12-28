package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniqueQuest {
    FIRST(QuestType.TASK, "町の草むしり", 10, 0, Place.CITY);

    QuestType type;
    String name;
    int difficulty;
    int danger;
    Place place;

    public Quest getInstance() {
        return new Quest(type, name, difficulty, danger, place);
    }
}
