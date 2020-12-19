package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    void insert(Person person, long worldId);

    List<Person> select(long worldId);

    void update(Person person);

    void delete(long personId);

    void updateIsAdventurer(long id, boolean isAdventurer);
}
