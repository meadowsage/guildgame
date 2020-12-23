package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddQuestOrderUseCase {
    private final QuestOrderMapper questOrderMapper;

    public void run(long questId, long personId) {
        questOrderMapper.insert(new QuestOrder(questId, personId));
    }
}
