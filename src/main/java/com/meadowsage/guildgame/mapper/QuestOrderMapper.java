package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.person.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestOrderMapper {
    void insert(Person person, Quest quest);

    void delete(long worldId);
}
