package com.meadowsage.guildgame.model;

public class Quest {
    QuestType type;
    int difficulty;

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
