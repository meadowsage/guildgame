package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Money {
    @Getter
    private long value;

    public void add(long value) {
        this.value += value;
        if(this.value < 0) this.value = 0;
    }

    public static Money of(long value) {
        Money money = new Money();
        money.value = value > 0 ? value : 0;
        return money;
    }
}
