package com.meadowsage.guildgame.usecase.party;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.repository.PartyRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetFreePartiesUseCase {
    private final QuestRepository questRepository;
    private final PartyRepository partyRepository;

    public GetFreePartiesUseCaseResult run(long worldId, long questId) {
        Quest quest = questRepository.get(questId).orElseThrow(IllegalArgumentException::new);
        List<Party> parties = partyRepository.getFreeParties(worldId);
        return new GetFreePartiesUseCaseResult(parties, quest);
    }

    @Data
    @AllArgsConstructor
    public static class GetFreePartiesUseCaseResult {
        List<Party> parties;
        Quest quest;
    }
}
