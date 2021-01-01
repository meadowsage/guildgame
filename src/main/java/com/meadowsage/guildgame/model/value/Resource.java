package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 現在値と最大値をもつリソース
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Resource {
    @Getter
    private int max;
    @Getter
    private int value;

    public void restore(int value) {
        this.value = Math.min(this.value + value, max);
    }

    public void consume(int value) {
        this.value = Math.max(this.value - value, 0);
    }

    public void recoverToMax() {
        value = max;
    }

    public static Resource of(int max) {
        return of(max, max);
    }

    public static Resource of(int value, int max) {
        Resource energy = new Resource();
        energy.max = Math.max(value, 1);
        energy.value = Math.max(value, 0);
        return energy;
    }

    public boolean isFull() {
        return value == max;
    }
}
