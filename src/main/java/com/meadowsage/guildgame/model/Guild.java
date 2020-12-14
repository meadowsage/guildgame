package com.meadowsage.guildgame.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Guild {
    @Getter
    private int money;
    @Getter
    private int reputation;

    public static Guild create() {
        Guild guild = new Guild();
        guild.money = 10000;
        guild.reputation = 100; // TODO 所属する冒険者の名声合計
        return guild;
    }

    public void accountingProcess() {
        this.money += 100;
    }
}
