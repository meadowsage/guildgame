package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.QuestMapper;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestRepository {

    private final QuestMapper questMapper;

    public List<Quest> getOngoingQuests(long worldId) {
        return questMapper.selectAll(worldId, QuestOrder.State.ONGOING);
    }
}
