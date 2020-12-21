package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.system.GameLogger;
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
        guild.reputation = 40;
        return guild;
    }

    public void accountingProcess(GameLogger gameLogger) {
        this.money.add(1000);
        gameLogger.add("1000Gの報酬を受け取った。");
        this.money.add(-600);
        gameLogger.add("600Gの維持費を支払った。");
    }
}
