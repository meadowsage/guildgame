package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reputation {

    private static final int MIN = 0;
    private static final int MAX = 9999;

    @Getter
    private int value;

    public Reputation add(int value) {
        this.value = Math.min(Math.max(this.value + value, MIN), MAX);
        return this;
    }

    public static Reputation of(int value) {
        Reputation reputation = new Reputation();
        reputation.value = Math.min(Math.max(value, MIN), MAX);
        return reputation;
    }
}
