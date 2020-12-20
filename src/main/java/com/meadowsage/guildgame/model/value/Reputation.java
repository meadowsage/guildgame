package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reputation extends PositiveValue{

    public static Reputation of(long value) {
        Reputation reputation = new Reputation();
        reputation.value = value > 0 ? value : 0;
        return reputation;
    }
}
