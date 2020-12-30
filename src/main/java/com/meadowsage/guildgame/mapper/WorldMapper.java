package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorldMapper {
    void save(GameWorld world, String saveDataId);

    World select(long worldId);

    World selectBySaveDataId(String saveDataId);

    void update(GameWorld world);
}
