package com.meadowsage.guildgame.usecase.party;

import com.meadowsage.guildgame.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RemovePartyMemberUseCase {
    private final PartyRepository partyRepository;

    public void run(long partyId, long personId) {
        partyRepository.removeMember(partyId, personId);
    }
}
