package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Money {

    private static final int MIN = 0;
    private static final int MAX = 99999999;

    private long value;

    public int getValue() {
        return (int) value;
    }

    public Money add(int value) {
        this.value = Math.min(Math.max(this.value + value, MIN), MAX);
        return this;
    }

    public Money add(Money money) {
        this.add((int) money.value);
        return this;
    }

    public Money subtract(int value) {
        return add(value * -1);
    }

    public Money subtract(Money money) {
        return subtract(money.getValue());
    }

    public static Money of(int value) {
        Money money = new Money();
        money.value = Math.min(Math.max(value, MIN), MAX);
        return money;
    }
}
