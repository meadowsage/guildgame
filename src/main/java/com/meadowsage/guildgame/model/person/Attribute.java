package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Attribute {
    private static final double MU = 1.0; // ln(x)の平均 μ scale 大きいほど分布の右側が大きくなる
    private static final double SIGMA = 0.2; // ln(x)の標準偏差 σ shape 大きいほど分布が横に広がる
    private static final LogNormalDistribution logNormalDistribution = new LogNormalDistribution(MU, SIGMA);

    @Getter
    private int value;
    private int exp;
    private Type type;

    private Attribute(int value, Type type) {
        this.value = value;
        this.exp = value * value;
        this.type = type;
    }

    public String getName() {
        return type.label;
    }

    public void earnExp(int earned, Person person, Quest quest, @Nullable GameLogger gameLogger) {
        exp += earned;
        if (gameLogger != null)
            gameLogger.detail(person.getName().getFirstName() + "は" + getName() + "経験値" + earned + "を獲得", person, quest);

        if (!haveEnoughExp()) return;

        while (haveEnoughExp()) {
            value++;
        }

        if (gameLogger != null)
            gameLogger.important(person.getName().getFirstName() + "の" + getName() + "が" + value + "に上がった！", person, quest);
    }

    private boolean haveEnoughExp() {
        return exp > Math.pow(value + 1, 2);
    }

    public static Attribute of(int value, Type type) {
        return new Attribute(value, type);
    }

    public static Attribute generateRandom(Type type) {
        int value = getRandomValue();
        return new Attribute(value, type);
    }

    /**
     * 対数正規分布でランダム値を生成
     */
    private static int getRandomValue() {
        int value = ((int) (logNormalDistribution.sample() * 20) - 30) % 150;
        return value > 0 ? value : 1;
    }

    @AllArgsConstructor
    public enum Type {
        BATTLE("戦闘"),
        KNOWLEDGE("知識"),
        SUPPORT("支援");

        public final String label;
    }
}
