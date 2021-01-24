package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.ApplicantMapper;
import com.meadowsage.guildgame.mapper.PersonMapper;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final PersonMapper personMapper;
    private final ApplicantMapper applicantMapper;

    public Optional<Adventurer> getAdventurer(long personId) {
        List<Adventurer> adventurers = personMapper.selectAdventurers(
                null, Collections.singletonList(personId), null);
        if (adventurers.isEmpty()) return Optional.empty();
        else return Optional.of(adventurers.get(0));
    }

    public List<Adventurer> getAdventurers(long worldId) {
        return personMapper.selectAdventurers(worldId, null, null);
    }

    public List<Adventurer> getAdventurers(List<Long> ids) {
        if (ids.isEmpty()) return new ArrayList<>();
        return personMapper.selectAdventurers(null, ids, null);
    }

    public List<Applicant> getApplicants(long worldId) {
        return personMapper.selectApplicants(worldId);
    }

    public void delete(long personId) {
        personMapper.delete(personId);
    }

    public void deleteApplicant(long personId) {
        applicantMapper.delete(personId);
    }

    public void deleteAllApplicants(long worldId) {
        getApplicants(worldId).stream().map(Applicant::getId).forEach(this::deleteApplicant);
    }

    public void save(Person person, long worldId) {
        if (person.isNew()) {
            personMapper.insert(person, worldId);
            personMapper.insertPersonImage(person.getId(), person.getPersonImage());
            personMapper.insertPersonalities(person);
            personMapper.insertSkills(person);
            if (person instanceof Applicant) applicantMapper.insert(person);
        } else {
            personMapper.update(person);
            person.getSkills().forEach(personSkill -> personMapper.updateSkill(person.getId(), personSkill));
        }
    }

    public void save(List<Adventurer> persons, long worldId) {
        persons.forEach(person -> save(person, worldId));
    }
}
