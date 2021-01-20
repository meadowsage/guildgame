package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GuildMapper;
import com.meadowsage.guildgame.model.Guild;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildRepository {

    private final GuildMapper guildMapper;

    public Guild get(long worldId) {
        return guildMapper.select(worldId);
    }

    public void create(Guild guild, long worldId) {
        guildMapper.insert(guild, worldId);
    }

    public void save(Guild guild, long worldId) {
        guildMapper.update(guild, worldId);
    }
}
