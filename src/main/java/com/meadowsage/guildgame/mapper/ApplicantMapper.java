package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicantMapper {
    void insert(Person person);

    void delete(long personId);
}
