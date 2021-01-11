package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonSkill {
    @Getter
    private Skill skill;
    @Getter
    private int level;
    @Getter
    private long exp;

    public static PersonSkill of(Skill skill, int level) {
        return new PersonSkill(skill, level, (int) (100 * Math.pow(level - 1, 2)));
    }

    public static PersonSkill generateRandom(Dice dice) {
        Skill skill = Skill.getRandom(dice);
        int level = dice.roll(1, 8);
        return of(skill, level);
    }
}
