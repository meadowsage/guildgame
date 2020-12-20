package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Money extends PositiveValue{

    public static Money of(long value) {
        Money money = new Money();
        money.value = value > 0 ? value : 0;
        return money;
    }
}
