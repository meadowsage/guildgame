package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Energy extends PositiveValue {

    public static Energy of(long value) {
        Energy energy = new Energy();
        energy.value = value > 0 ? value : 0;
        return energy;
    }
}
