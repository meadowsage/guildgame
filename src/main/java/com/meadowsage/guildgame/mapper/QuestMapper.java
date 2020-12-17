package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.quest.Quest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestMapper {
    void insert(Quest quest, long worldId);

    void update(Quest quest);

    List<Quest> select(long worldId);
}
