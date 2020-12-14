package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.Guild;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuildMapper {
    void save(Guild guild, long worldId);

    Guild select(long worldId);

    void update(Guild guild, long worldId);
}
