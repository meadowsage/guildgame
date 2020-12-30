package com.meadowsage.guildgame.model.accounting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildBalance {
    private final long worldId;
    private final int value;
    private final int gameDate;
}
