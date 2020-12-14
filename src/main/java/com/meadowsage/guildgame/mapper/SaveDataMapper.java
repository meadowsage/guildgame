package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.system.SaveData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SaveDataMapper {
    void save(SaveData saveData);
    void select(String id);
}
