package com.meadowsage.guildgame.usecase.party;

import com.meadowsage.guildgame.model.person.PartyNameGenerator;
import com.meadowsage.guildgame.model.system.Dice;
import org.springframework.stereotype.Service;

@Service
public class GetRandomPartyNameUseCase {

    public String run() {
        PartyNameGenerator partyNameGenerator = new PartyNameGenerator(new Dice());
        return partyNameGenerator.generate();
    }
}
