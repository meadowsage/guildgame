package com.meadowsage.guildgame.model.system;

import java.util.stream.IntStream;

public class Dice {
    public int roll(int number, int sides, int modifier) {
        int result = IntStream.range(0, number).map(_value -> calc(sides)).sum() + modifier;
        System.out.println("[" + number + "d" + sides + (modifier >= 0 ? "+" : "") + modifier + " -> " + result + "]");
        return result;
    }

//    public int roll(int number, int sides) {
//        return roll(number, sides);
//    }

//    public boolean largerThan(int number, int sides, int modifier, int target) {
//        return roll(number,sides,modifier) >= target;
//    }

    /**
     * 1d100の結果に応じた結果を返す
     */
    public RollResult calcResult(int target) {
        System.out.println("[目標値 " + target + "]");
        int result = roll(1,100, 0);

        // 1,100は自動成功・自動失敗
        if(result == 1) return RollResult.CRITICAL;
        else if(result == 100) return RollResult.FUMBLE;

        if (result <= target && result <= 5) return RollResult.CRITICAL;
        else if (result <= (target / 5)) return RollResult.SPECIAL;
        else if(result <= target) return RollResult.SUCCESS;
        else if(result >= 96) return RollResult.FUMBLE;
        else return RollResult.FAILURE;
    }

    private int calc(int sides) {
        return (int)(Math.random() * sides + 1);
    }

    public enum RollResult {
        CRITICAL,
        SPECIAL,
        SUCCESS,
        FAILURE,
        FUMBLE
    }
}
