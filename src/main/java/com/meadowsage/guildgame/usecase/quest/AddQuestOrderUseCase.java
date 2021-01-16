package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddQuestOrderUseCase {
    private final QuestRepository questRepository;

    public void run(long questId, long partyId) {
        questRepository.addQuestOrder(questId, partyId);
    }
}
