package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.Quest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestMapper {
    void save(Quest quest, long worldId);

    List<Quest> select(long worldId);
}
