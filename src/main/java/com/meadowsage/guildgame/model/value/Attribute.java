package com.meadowsage.guildgame.model.value;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.distribution.LogNormalDistribution;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Attribute {
    private static final double MU = 0.8; // ln(x)の平均 μ scale 大きいほど分布の右側が大きくなる
    private static final double SIGMA = 0.35; // ln(x)の標準偏差 σ shape 大きいほど分布が横に広がる
    private static final LogNormalDistribution logNormalDistribution = new LogNormalDistribution(MU, SIGMA);

    @Getter
    private int value;

    public static Attribute of(int value) {
        return new Attribute(value);
    }

    public static Attribute generateRandom() {
        return new Attribute(getRandomValue());
    }

    /**
     * 対数正規分布でランダム値を生成
     */
    private static int getRandomValue() {
        int value = ((int)(logNormalDistribution.sample() * 20) - 15) % 200;
        return value > 0 ? value : 1;
    }
}
