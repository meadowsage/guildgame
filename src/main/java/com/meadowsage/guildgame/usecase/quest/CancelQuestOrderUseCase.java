package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CancelQuestOrderUseCase {
    private final QuestOrderMapper questOrderMapper;

    public void run(long questOrderId) {
        questOrderMapper.delete(questOrderId);
    }
}
