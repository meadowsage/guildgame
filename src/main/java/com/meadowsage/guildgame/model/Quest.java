package com.meadowsage.guildgame.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quest {
    @Getter
    private long id;
    @Getter
    private QuestType type;
    @Getter
    private int difficulty;

    public static Quest generateRandom() {
        Quest quest = new Quest();
        quest.type = QuestType.values()[(int) (Math.random() * QuestType.values().length)];
        quest.difficulty = (int) (Math.random() * 100);
        return quest;
    }

    public enum QuestType {
        Explore,
        Hunt,
        Harvest,
        Chore;
    }
}
