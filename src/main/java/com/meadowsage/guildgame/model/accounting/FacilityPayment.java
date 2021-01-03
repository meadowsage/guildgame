package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FacilityPayment {
    private final String name;
    private final int value;
    private final int gameDate;

    public static FacilityPayment process(
            String name,
            int value,
            Guild guild,
            int gameDate,
            GameLogger gameLogger
    ) {
        FacilityPayment facilityPayment = new FacilityPayment(name, value, gameDate);

        guild.payMoney(Money.of(value));
        gameLogger.detail(name + "の維持費として" + value + "Gを支払った。");

        return facilityPayment;
    }
}
