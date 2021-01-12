package com.meadowsage.guildgame.usecase.person;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPartiesUseCase {
    private final PartyRepository partyRepository;

    public List<Party> run(long worldId) {
        return partyRepository.getAll(worldId);
    }
}
