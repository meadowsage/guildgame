package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestOrderMapper questOrderMapper;

    public void cancelQuestOrder(long questId, long personId) {
       questOrderMapper.delete(questId, personId);
    }

    public void addQuestOrder(long questId, long personId) {
        questOrderMapper.insert(questId, personId);
    }
}
