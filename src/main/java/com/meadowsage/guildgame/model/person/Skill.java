package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Skill {
    // サポートスキル
    OBSERVE("観察",false),
    COOKING("料理", false),
    ALCHEMY("錬金", false),
    SMITHING("鍛冶", false),
    TRAP("罠作成", false),
    FIRST_AID("応急手当", false),
    SNEAK("隠密", false),
    CAMP("野営", false),
    COACHING("指導", false),
    // 戦闘スキル
    SWORD("剣術", true),
    FIGHTING("格闘", true),
    ARCHERY("弓術", true),
    // 魔法スキル
    ATTACK_MAGIC("攻撃魔法", true),
    SUPPORT_MAGIC("支援魔法", true),
    HEALING_MAGIC("回復魔法", true);

    String label;
    boolean hasLevel;

    public static Skill getRandom(Dice dice) {
        return Skill.values()[dice.roll(1, Skill.values().length) - 1];
    }
}
