package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum QuestType {
    // 雑務
    TASK(0.1, 0.4, 2.5, 0.5),
    // 採集
    HARVEST(0.4, 1.8, 1.8, 1.0),
    // 狩猟
    HUNT(1.8, 0.8, 0.4, 1.3),
    // 探索
    EXPLORE(1.0, 1.0, 1.0, 1.5);

    @Getter
    private final double battleCoefficient;
    @Getter
    private final double knowledgeCoefficient;
    @Getter
    private final double supportCoefficient;
    @Getter
    private final double difficultyCoefficient;
}