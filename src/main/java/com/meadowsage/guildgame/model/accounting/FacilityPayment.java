package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FacilityPayment {
    private String name;
    private int value;
    private int gameDate;

    public static void process(
            String name,
            int value,
            Guild guild,
            int gameDate,
            GameLogger gameLogger,
            AccountingLogger accountingLogger
    ) {
        FacilityPayment facilityPayment = new FacilityPayment(name, value, gameDate);

        guild.payMoney(Money.of(value));
        gameLogger.detail(name + "の維持費として" + value + "Gを支払った。");

        accountingLogger.recordFacilityPayment(facilityPayment);
    }
}
