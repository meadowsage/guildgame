package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniquePerson {
    // Tellan Wallace
    TELLAN("テラン", "ウォレス",
            32, 34, 58, 1,
            2000, 30,
            Arrays.asList(Personality.KIND, Personality.LAZY),
            Arrays.asList(
                    PersonSkill.of(Skill.CAMP, 4),
                    PersonSkill.of(Skill.COACHING, 4),
                    PersonSkill.of(Skill.SWORD, 3)),
            2,
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

    private final int imageBodyId;

    private final boolean isAdventurer;

    public Person getInstance() {
        return Person.of(
                firstName,
                familyName,
                battle,
                knowledge,
                support,
                energy,
                money,
                reputation,
                personalities,
                personSkills,
                imageBodyId,
                isAdventurer);
    }
}
