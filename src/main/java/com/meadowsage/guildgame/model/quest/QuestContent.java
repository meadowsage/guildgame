package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Item;
import com.meadowsage.guildgame.model.Monster;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.*;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum QuestContent {
    // 町
    CITY_WEEDING("町の草むしり", Place.CITY, 500, 0, 1,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 20)),
            requirements(),
            new QuestDetailEvents() {
                // 必ず成功
                @Override
                public int specialEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    return successEvent(quest, adventurer, gameLogger);
                }

                @Override
                public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    gameLogger.detail(adventurer.getName().getFirstName() + "は淡々と草をむしった。", adventurer, quest);
                    return 1;
                }

                @Override
                public int failureEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    return successEvent(quest, adventurer, gameLogger);
                }

                @Override
                public int fumbleEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    return successEvent(quest, adventurer, gameLogger);
                }
            }),
    CITY_GUARD("町の見回り", Place.CITY, 500, 0, 1,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 20)),
            requirements(),
            new QuestDetailEvents() {
                @Override
                public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    gameLogger.detail(adventurer.getName().getFirstName() + "は見回りを難なくこなした。", adventurer, quest);
                    return 1;
                }
            }),
    CITY_ASSIST_HAUL("店の運搬手伝い", Place.CITY, 500, 0, 1,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 20)),
            requirements(),
            new QuestDetailEvents() {
                @Override
                public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    gameLogger.detail(adventurer.getName().getFirstName() + "は荷物の運搬を手伝った。", adventurer, quest);
                    return 1;
                }
            }),
    CITY_ASSIST_CONSTRUCTION("工事の手伝い", Place.CITY, 500, 0, 1,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 20)),
            requirements(),
            new QuestDetailEvents() {
                @Override
                public int successEvent(Quest quest, Adventurer adventurer, GameLogger gameLogger) {
                    String[] options = new String[]{"橋", "古くなった家屋", "道", "教会", "商店"};
                    gameLogger.detail(adventurer.getName().getFirstName() + "は"
                            + options[new Dice().roll(1, options.length) - 1]
                            + "の工事を手伝った。", adventurer, quest);
                    return 1;
                }
            }),
    CITY_EXTERMINATE_WILD_HOUNDS("郊外の野犬駆除", Place.CITY, 800, 0, 1,
            requirements(Requirement.of(Attribute.Type.BATTLE, 20)),
            requirements(),
            new QuestDetailEvents(new Monster("野犬") {
            }) {
            }),
    CITY_EXTERMINATE_LARGE_RATS("ラージラット駆除", Place.CITY, 800, 0, 1,
            requirements(Requirement.of(Attribute.Type.BATTLE, 20)),
            requirements(),
            new QuestDetailEvents(new Monster("ラージラット") {
            }) {
            }),
    // 茸の森
    M_FOREST_ESCORT_LUMBERJACK("木こりの護衛", Place.MUSHROOM_FOREST, 1000, 0, 2,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 50), Requirement.of(Attribute.Type.BATTLE, 30)),
            requirements(),
            new QuestDetailEvents() {
            }),
    M_FOREST_ESCORT_ROAD_MAINTENANCE("道の舗装手伝い", Place.MUSHROOM_FOREST, 1000, 0, 2,
            requirements(Requirement.of(Attribute.Type.SUPPORT, 50)),
            requirements(),
            new QuestDetailEvents() {
            }),
    M_FOREST_EXTERMINATE_FOREST_SLIME("フォレストスライム駆除", Place.MUSHROOM_FOREST, 1000, 1, 2,
            requirements(Requirement.of(Attribute.Type.BATTLE, 50)),
            requirements(),
            new QuestDetailEvents(new Monster("フォレストスライム") {
            }) {
            }),
    M_FOREST_HARVEST_MEDICINE_MUSHROOM("薬用キノコの採取", Place.MUSHROOM_FOREST, 1200, 0, 2,
            requirements(Requirement.of(Attribute.Type.KNOWLEDGE, 50)),
            requirements(),
            new QuestDetailEvents(new Item("薬用キノコ") {
            }) {
            });

    private final String contentName;
    private final Place place;
    private final int reward;
    private final int danger;
    private final int amount;
    private final List<Requirement> requirements;
    private final List<Requirement> recommends;
    private final QuestDetailEvents events;

    private static List<Requirement> requirements(Requirement... requirements) {
        return Collections.unmodifiableList(Arrays.asList(requirements));
    }

    public static List<QuestContent> valuesAt(Place place) {
        return Arrays.stream(values())
                .filter(questContent -> questContent.getPlace().equals(place))
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static abstract class Requirement {
        public abstract String getLabel();

        public abstract int getValue();

        public static Requirement of(Attribute.Type type, int value) {
            return new AttributeRequirement(type, value);
        }

        public static Requirement of(Skill skill, int value) {
            return new SkillRequirement(skill, value);
        }

        public abstract boolean isSatisfied(Party party);

        public abstract boolean isSatisfied(Adventurer adventurer);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AttributeRequirement extends Requirement {
        private final Attribute.Type type;
        @Getter
        private final int value;

        @Override
        public String getLabel() {
            return type.label;
        }

        @Override
        public boolean isSatisfied(Party party) {
            return party.getAttributeValue(type) >= value;
        }

        @Override
        public boolean isSatisfied(Adventurer adventurer) {
            return adventurer.getAttribute(type).getValue() >= value;
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SkillRequirement extends Requirement {
        private final Skill skill;
        @Getter
        private final int value;

        @Override
        public String getLabel() {
            return skill.getLabel();
        }

        @Override
        public boolean isSatisfied(Party party) {
            return party.getMaxSkillLevel(skill) >= value;
        }

        @Override
        public boolean isSatisfied(Adventurer adventurer) {
            Optional<PersonSkill> personSkill = adventurer.getSkill(skill);
            return personSkill.isPresent() && personSkill.get().getLevel() >= value;
        }
    }
}