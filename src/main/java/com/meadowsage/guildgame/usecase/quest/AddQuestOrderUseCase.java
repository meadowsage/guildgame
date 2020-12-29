package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddQuestOrderUseCase {
    private final QuestRepository questRepository;
    private final PersonRepository personRepository;
    private final QuestOrderMapper questOrderMapper;

    public void run(long questId, long personId) {
        Quest quest = questRepository.get(questId).orElseThrow(IllegalStateException::new);
        Adventurer adventurer = personRepository.getAdventurer(personId).orElseThrow(IllegalStateException::new);
        questOrderMapper.insert(new QuestOrder(quest, adventurer));
    }
}
