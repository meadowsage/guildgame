package com.meadowsage.guildgame.usecase.person;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddPartyMemberUseCase {
    private final PartyRepository partyRepository;

    public void run(long partyId, long personId) {
        Party party = partyRepository.get(partyId).orElseThrow(IllegalArgumentException::new);
        if(!party.canAddMember()) throw new IllegalStateException();
        partyRepository.addMember(partyId, personId);
    }
}
