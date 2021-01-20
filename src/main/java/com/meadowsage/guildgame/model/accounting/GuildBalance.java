package com.meadowsage.guildgame.model.accounting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GuildBalance {
    private final long worldId;
    private final int value;
    private final int gameDate;

    public static void record(
            long worldId,
            int value,
            int gameDate,
            AccountingLogger accountingLogger
    ) {
        GuildBalance guildBalance = new GuildBalance(worldId, value, gameDate);
        accountingLogger.recordGuildBalance(guildBalance);
    }
}
