package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class PositiveValue {
    @Getter
    long value;

    public void add(long value) {
        this.value += value;
        if(this.value < 0) this.value = 0;
    }
}
