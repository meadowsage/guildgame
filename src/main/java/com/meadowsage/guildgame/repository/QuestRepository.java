package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.QuestMapper;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestRepository {

    private final QuestMapper questMapper;

    public List<Quest> getOngoingQuests(long worldId) {
        return questMapper.select(worldId, null, QuestOrder.State.ONGOING);
    }

    public Optional<Quest> get(long questId) {
        List<Quest> result = questMapper.select(null, questId, null);
        if(result.isEmpty()) return Optional.empty();
        else return Optional.of(result.get(0));
    }

    public void save(Quest quest, long worldId) {
        if (quest.isNew()) questMapper.insert(quest, worldId);
        else questMapper.update(quest);
    }
}
