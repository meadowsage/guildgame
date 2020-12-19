package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Attributes {
    @Getter
    private int battle;
    @Getter
    private int knowledge;
    @Getter
    private int support;

    public static Attributes generateRandom() {
        Attributes attributes = new Attributes();
        Random random = new Random();
        attributes.battle = generateRandom(random);
        attributes.knowledge = generateRandom(random);
        attributes.support = generateRandom(random);
        return attributes;
    }

    /**
     * ランダム値生成
     */
    private static int generateRandom(Random random) {
        int value = (int) (random.nextGaussian() * 14) + 35;
        return value > 0 ? value : 1;
    }
}
