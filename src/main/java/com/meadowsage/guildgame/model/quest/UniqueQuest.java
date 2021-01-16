package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static com.meadowsage.guildgame.model.quest.QuestContent.CITY_WEEDING;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniqueQuest {
    FIRST_QUEST(CITY_WEEDING);

    QuestContent content;

    public Quest getInstance() {
        return new Quest(content);
    }
}
