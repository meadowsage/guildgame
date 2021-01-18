package com.meadowsage.guildgame.usecase.party;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.repository.PartyRepository;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreatePartyUseCase {
    private final PersonRepository personRepository;
    private final PartyRepository partyRepository;

    public void run(long worldId, String name, List<Long> memberIds) {
        List<Adventurer> members = memberIds.stream().map(id ->
                personRepository.getAdventurer(id).orElseThrow(IllegalArgumentException::new)
        ).collect(Collectors.toList());

        partyRepository.save(Party.createNew(name, members), worldId);
    }
}
