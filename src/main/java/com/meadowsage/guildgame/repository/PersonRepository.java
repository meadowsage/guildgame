package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.ApplicantMapper;
import com.meadowsage.guildgame.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final PersonMapper personMapper;
    private final ApplicantMapper applicantMapper;

    public void delete(long personId){
        personMapper.delete(personId);
    }

    public void deleteApplicant(long personId) {
        applicantMapper.delete(personId);
    }
}
