package com.meadowsage.guildgame.model.person;

import lombok.Getter;

import java.util.Random;

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
     * 40を中心に正規分布
     */
    private static int generateRandom(Random random) {
        int value = (int) (random.nextGaussian() * 10) + 50;
//        int value = (int) (random.nextGaussian() * 9) + 40;
        return value > 0 ? value : 1;
    }
}
