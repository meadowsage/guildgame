package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.World;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorldMapper {
    void save(World world, String saveDataId);

    World select(String saveDataId);

    void update(World world);
}
