package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    void insert(Person person, long worldId);

    List<Adventurer> selectAdventurers(Long worldId, List<Long> ids, Boolean notOrdering);

    List<Applicant> selectApplicants(long worldId);

    void update(Person person);

    void delete(long personId);
}
