package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.PartyMapper;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PartyRepository {

    private final PersonRepository personRepository;
    private final PartyMapper partyMapper;

    public List<Party> getAll(long worldId) {
        List<Party> parties = partyMapper.selectAll(worldId);
        parties.forEach(party -> {
            List<Long> partyMemberIds = partyMapper.selectPartyMemberIds(party.getId());
            List<Adventurer> partyMembers = personRepository.getAdventurers(worldId, partyMemberIds);
            party.getPartyMembers().addAll(partyMembers);
        });
        return parties;
    }

    public void addMember(long partyId, long personId) {
        partyMapper.insertPartyMembers(partyId, Collections.singletonList(personId));
    }

    public void removeMember(long partyId, long personId) {
        partyMapper.deletePartyMember(partyId, personId);
    }

    public void save(Party party, long worldId) {
        if (party.isNew()) {
            partyMapper.insert(party, worldId);
            partyMapper.insertPartyMembers(
                    party.getId(),
                    party.getPartyMembers().stream().map(Adventurer::getId).collect(Collectors.toList())
            );
        }
    }

    public void delete(long partyId) {
        partyMapper.delete(partyId);
    }

    public Optional<Party> get(long partyId) {
        return Optional.ofNullable(partyMapper.select(partyId));
    }
}
