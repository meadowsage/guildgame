package com.meadowsage.guildgame.usecase.party;

import com.meadowsage.guildgame.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdatePartyNameUseCase {
    private final PartyRepository partyRepository;

    public void run(long partyId, String partyName) {
        partyRepository.updatePartyName(partyId, partyName);
    }
}
