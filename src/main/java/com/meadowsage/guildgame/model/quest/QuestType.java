package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum QuestType {
    // 雑務
    TASK(0.1, 0.4, 3.0, 0.5),
    // 採集
    HARVEST(0.3, 2.0, 1.2, 1.0),
    // 狩猟
    HUNT(2.5, 0.6, 0.4, 1.2),
    // 探索
    EXPLORE(1.16, 1.16, 1.16, 1.4);

    // 能力の適性補正
    // 合計を3.5にする -> 能力の平均値が難易度に達していなくても、適性があればクリアできるようにするため
    @Getter
    private final double battleCoefficient;
    @Getter
    private final double knowledgeCoefficient;
    @Getter
    private final double supportCoefficient;
    // 難易度補正
    @Getter
    private final double difficultyCoefficient;
}