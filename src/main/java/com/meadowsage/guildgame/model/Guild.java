package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Guild {
    @Getter
    private Money money;
    @Getter
    private int reputation;

    public static Guild create() {
        Guild guild = new Guild();
        guild.money = Money.of(1000);
        guild.reputation = 10;
        return guild;
    }

    public void earnMoney(Money money) {
        this.money.add(money);
    }

    public void payMoney(Money reward) {
        this.money.subtract(reward);
    }

    public void gainReputation(int reputation) {
        this.reputation += reputation;
    }

    public int getLimitOfAdventurers() {
        // FIXME reputationまたはランクで判定
        return 6;
    }

    public int getLimitOfQuests() {
        // FIXME reputationまたはランクで判定
        return 3;
    }

    public int getLimitOfApplicants() {
        // FIXME とりあえず固定
        return 5;
    }
}
