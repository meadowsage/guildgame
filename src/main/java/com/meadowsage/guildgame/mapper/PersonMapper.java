package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.system.SaveData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    void save(Person person, long worldId);

    List<Person> select(long worldId);
}
