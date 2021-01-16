package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CancelQuestOrderUseCase {
    private final QuestRepository questRepository;

    public void run(long questOrderId) {
        questRepository.cancelQuestOrder(questOrderId);
    }
}
