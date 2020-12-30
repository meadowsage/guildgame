package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.accounting.GuildBalance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuildBalanceMapper {
    void insert(GuildBalance guildBalance);
    GuildBalance select(long worldId, int gameDate);
}
