package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.PartyMapper;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartyRepository {

    private final PersonRepository personRepository;
    private final PartyMapper partyMapper;

    public List<Party> getAll(long worldId) {
        List<Party> parties = partyMapper.select(worldId);
        parties.forEach(party -> {
            List<Long> partyMemberIds = partyMapper.selectPartyMemberIds(party.getId());
            List<Adventurer> partyMembers = personRepository.getAdventurers(worldId, partyMemberIds);
            party.getPartyMembers().addAll(partyMembers);
        });
        return parties;
    }

    public void addMember(long worldId, long personId) {
        // TODO
    }

    public void removeMember(long partyId, long personId) {
        // TODO
    }

    public void save(Party party, long worldId) {
        if (party.isNew()) {
            partyMapper.insert(party, worldId);
            partyMapper.insertPartyMembers(party);
        }
    }

    public void delete(long partyId) {
        // TODO
    }

}
