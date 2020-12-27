package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.mapper.QuestMapper;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetQuestOrderablesUseCase {
    private final QuestMapper questMapper;
    private final PersonRepository personRepository;

    /**
     * クエストの発注が可能な冒険者と、発注時の報酬見積を返します
     */
    public GetQuestOrderablesUseCaseResult run(long worldId, long questId) {
        Quest quest = questMapper.select(questId);
        List<Adventurer> adventurers = personRepository.getOrderables(worldId);

        return new GetQuestOrderablesUseCaseResult(
                adventurers.stream().collect(Collectors.toMap(
                        person -> person,
                        person -> person.calcRewards(quest)
                )));
    }

    @Data
    @AllArgsConstructor
    public static class GetQuestOrderablesUseCaseResult {
        Map<Adventurer, Long> rewardsByPerson;
    }
}
