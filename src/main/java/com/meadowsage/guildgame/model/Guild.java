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
        guild.money = Money.of(10000);
        guild.reputation = 300; // TODO 所属する冒険者の名声合計?
        return guild;
    }

    public void accountingProcess() {
        this.money.add(1000);
    }
}
