package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestContent {
    // 町
    CITY_WEEDING("草むしり", new QuestDetailEvents() {
        // 必ず成功
        @Override
        public int criticalEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            gameLogger.detail(adventurer.getName().getFirstName() + "は淡々と草をむしった。", adventurer, quest);
            return 1;
        }

        @Override
        public int specialEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            return criticalEvent(quest, adventurer, gameLogger);
        }

        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            return criticalEvent(quest, adventurer, gameLogger);
        }

        @Override
        public int failureEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            return criticalEvent(quest, adventurer, gameLogger);
        }

        @Override
        public int fumbleEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            return criticalEvent(quest, adventurer, gameLogger);
        }
    }),
    CITY_GUARD("警備", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            gameLogger.detail(adventurer.getName().getFirstName() + "は警備を難なくこなした。", adventurer, quest);
            return 1;
        }
    }),
    CITY_ASSIST_HAUL("運搬手伝い", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            gameLogger.detail(adventurer.getName().getFirstName() + "は荷物の運搬を手伝った。", adventurer, quest);
            return 1;
        }
    }),
    CITY_ASSIST_CONSTRUCTION("工事手伝い", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            String[] options = new String[]{"橋", "古くなった家屋", "道", "教会", "商店"};
            gameLogger.detail(adventurer.getName().getFirstName() + "は"
                    + options[new Dice().roll(1, options.length) - 1]
                    + "の工事を手伝った。", adventurer, quest);
            return 1;
        }
    });

    private final String contentName;
    private final QuestDetailEvents events;
}