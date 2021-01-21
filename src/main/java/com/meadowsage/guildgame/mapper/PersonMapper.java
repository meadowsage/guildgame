package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    void insert(Person person, long worldId);

    void insertPersonImage(Long personId, PersonImage personImage);

    void insertPersonalities(Person person);

    void insertSkills(Person person);

    List<Adventurer> selectAdventurers(Long worldId, List<Long> ids, Boolean notOrdering);

    List<Applicant> selectApplicants(long worldId);

    void update(Person person);

    void updateSkill(long personId, PersonSkill personSkill);

    void delete(long personId);
}
