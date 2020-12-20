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
        attributes.battle = getRandomValue(random);
        attributes.knowledge = getRandomValue(random);
        attributes.support = getRandomValue(random);
        return attributes;
    }

    public static Attributes of(int battle, int knowledge, int support) {
        Attributes attributes = new Attributes();
        attributes.battle = battle;
        attributes.knowledge = knowledge;
        attributes.support = support;
        return attributes;
    }

    /**
     * 正規分布でランダム値を生成
     */
    private static int getRandomValue(Random random) {
        int value = (int) (random.nextGaussian() * 14) + 35;
        return value > 0 ? value : 1;
    }
}
