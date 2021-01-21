package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniquePerson {
    // Tellan Wallace
    TELLAN("テラン", "ウォレス",
            32, 34, 58, 1,
            2000, 30,
            Collections.singletonList(Personality.FRIENDLY),
            Arrays.asList(
                    PersonSkill.of(Skill.CAMP, 4),
                    PersonSkill.of(Skill.COACHING, 4),
                    PersonSkill.of(Skill.SWORD, 3)),
            "teran",
            true);

    private final String firstName;
    private final String familyName;

    private final int battle;
    private final int knowledge;
    private final int support;
    private final int energy;

    private final int money;
    private final int reputation;

    private final List<Personality> personalities;
    private final List<PersonSkill> personSkills;

    private final String imageBody;

    private final boolean isAdventurer;

    public Person getInstance() {
        return Person.of(
                firstName, familyName,
                battle, knowledge, support, energy, money, reputation,
                personalities, personSkills,
                imageBody, "0", "0", "0", "0",
                isAdventurer);
    }
}
