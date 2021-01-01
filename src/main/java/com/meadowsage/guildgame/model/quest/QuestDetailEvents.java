package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Item;
import com.meadowsage.guildgame.model.Monster;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
abstract class QuestDetailEvents {

    @Nullable
    Monster monster;
    @Nullable
    Item item;

    public QuestDetailEvents() {
    }

    public QuestDetailEvents(Monster monster) {
        this.monster = monster;
    }

    public QuestDetailEvents(Item item) {
        this.item = item;
    }

    public int criticalEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 超成功！[成功度+++]", adventurer, quest);
        return 3;
    }

    ;

    public int specialEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 大成功！[成功度++]", adventurer, quest);
        return 2;
    }

    ;

    public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 成功！[成功度+]", adventurer, quest);
        return 1;
    }

    ;

    public int failureEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 失敗…[体力-]", adventurer, quest);
        return 0;
    }

    ;

    public int fumbleEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
        gameLogger.detail(adventurer.getName().getFirstName() + ": 大失敗…[体力--]", adventurer, quest);
        return -1;
    }

    ;
}
