package com.meadowsage.guildgame.model.system;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.IntStream;

public class Dice {
    /**
     * 出目の数値を返す
     *
     * @param number ダイス数
     * @param sides 面数
     * @param modifier 補正値
     * @return 出目
     */
    public int roll(int number, int sides, int modifier) {
        int result = IntStream.range(0, number).map(_value -> calc(sides)).sum() + modifier;
        System.out.println("[" + number + "d" + sides + (modifier >= 0 ? "+" : "") + modifier + " -> " + result + "]");
        return result;
    }

    public int roll(int number, int sides) {
        return roll(number, sides, 0);
    }

//    public boolean largerThan(int number, int sides, int modifier, int target) {
//        return roll(number,sides,modifier) >= target;
//    }

    /**
     * 1d100の結果に応じた結果を返す
     */
    public DiceRollResult calcResult(int target, int modifier) {
        System.out.println("[目標値 " + target + "]");
        int result = roll(1, 100, modifier);

        // 1,100は自動成功・自動失敗
        if (result == 1) return new DiceRollResult(result, ResultType.CRITICAL);
        else if (result == 100) return new DiceRollResult(result, ResultType.FUMBLE);

        if (result <= target && result <= 5) return new DiceRollResult(result, ResultType.CRITICAL);
        else if (result <= (target / 5)) return new DiceRollResult(result, ResultType.SPECIAL);
        else if (result <= target) return new DiceRollResult(result, ResultType.SUCCESS);
        else if (result >= 96) return new DiceRollResult(result, ResultType.FUMBLE);
        else return new DiceRollResult(result, ResultType.FAILURE);
    }

    private int calc(int sides) {
        return (int) (Math.random() * sides + 1);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DiceRollResult {
        int number;
        ResultType type;
    }

    public enum ResultType {
        CRITICAL,
        SPECIAL,
        SUCCESS,
        FAILURE,
        FUMBLE
    }
}
