package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.system.GameLogger;

abstract class QuestDetailEvents {

    String name;

    public int criticalEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 超成功！[成功度+++]", adventurer, quest);
        return 3;
    };
    public int specialEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 大成功！[成功度++]", adventurer, quest);
        return 2;
    };
    public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 成功！[成功度+]", adventurer, quest);
        return 1;
    };
    public int failureEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 失敗…[体力-]", adventurer, quest);
        return 0;
    };
    public int fumbleEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 大失敗…[体力--]", adventurer, quest);
        return -1;
    };
}
