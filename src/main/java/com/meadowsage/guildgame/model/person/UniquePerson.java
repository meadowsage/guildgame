package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniquePerson {
    // Tellan Wallace
    TELLAN("テラン", "ウォレス", 28, 34, 45, 1, 2000, 30, true);

    private final String firstName;
    private final String familyName;
    private final int battle;
    private final int knowledge;
    private final int support;
    private final int energy;
    private final int money;
    private final int reputation;
    private final boolean isAdventurer;

    public Person getInstance() {
        return Person.of(firstName, familyName, battle, knowledge, support, energy, money, reputation, isAdventurer);
    }
}
