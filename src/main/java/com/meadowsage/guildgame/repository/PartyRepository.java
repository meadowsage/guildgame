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

    public Optional<Party> get(long partyId) {
        List<Party> parties = partyMapper.select(null, partyId, null);
        if (parties.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(withPartyMembers(parties.get(0)));
    }

    public List<Party> getAll(long worldId) {
        List<Party> parties = partyMapper.select(worldId, null, null);
        parties.forEach(this::withPartyMembers);
        return parties;
    }

    public List<Party> getFreeParties(long worldId) {
        return partyMapper.select(worldId, null, true).stream().map(this::withPartyMembers).collect(Collectors.toList());
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
                    party.getMembers().stream().map(Adventurer::getId).collect(Collectors.toList())
            );
        }
    }

    public void updatePartyName(long partyId, String partyName) {
        partyMapper.updatePartyName(partyId, partyName);
    }

    public void delete(long partyId) {
        partyMapper.delete(partyId);
    }

    private Party withPartyMembers(Party party) {
        if (party == null) return null;
        List<Long> partyMemberIds = partyMapper.selectPartyMemberIds(party.getId());
        List<Adventurer> partyMembers = personRepository.getAdventurers(partyMemberIds);
        party.getMembers().addAll(partyMembers);
        return party;
    }
}
