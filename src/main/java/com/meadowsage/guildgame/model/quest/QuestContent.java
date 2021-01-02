package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Item;
import com.meadowsage.guildgame.model.Monster;
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
    CITY_GUARD("町の見回り", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            gameLogger.detail(adventurer.getName().getFirstName() + "は見回りを難なくこなした。", adventurer, quest);
            return 1;
        }
    }),
    CITY_ASSIST_HAUL("店の運搬手伝い", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            gameLogger.detail(adventurer.getName().getFirstName() + "は荷物の運搬を手伝った。", adventurer, quest);
            return 1;
        }
    }),
    CITY_ASSIST_CONSTRUCTION("工事の手伝い", new QuestDetailEvents() {
        @Override
        public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
            String[] options = new String[]{"橋", "古くなった家屋", "道", "教会", "商店"};
            gameLogger.detail(adventurer.getName().getFirstName() + "は"
                    + options[new Dice().roll(1, options.length) - 1]
                    + "の工事を手伝った。", adventurer, quest);
            return 1;
        }
    }),
    CITY_EXTERMINATE_WILD_HOUNDS("野犬駆除", new QuestDetailEvents(new Monster("野犬") {
    }) {
    }),
    CITY_EXTERMINATE_LARGE_RATS("ラージラット駆除", new QuestDetailEvents(new Monster("ラージラット") {
    }) {
    }),
    // 茸の森
    M_FOREST_ESCORT_LUMBERJACK("木こりの護衛", new QuestDetailEvents() {
    }),
    M_FOREST_ESCORT_ROAD_MAINTENANCE("道の舗装", new QuestDetailEvents() {
    }),
    M_FOREST_EXTERMINATE_FOREST_SLIME("フォレストスライム駆除", new QuestDetailEvents(new Monster("フォレストスライム") {
    }) {
    }),
    M_FOREST_HARVEST_MEDICINE_MUSHROOM("薬用キノコの採取", new QuestDetailEvents(new Item("薬用キノコ") {
    }) {
    }),
    M_FOREST_EXPLORE("探索", new QuestDetailEvents() {
    }),
    ;

    private final String contentName;
    private final QuestDetailEvents events;
}