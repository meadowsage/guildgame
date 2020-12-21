package com.meadowsage.guildgame.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestOrderMapper {
    void insert(long questId, long personId);

    void delete(long questId, long personId);

    void deleteAll(long worldId);
}
