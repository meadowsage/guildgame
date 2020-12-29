package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.EstimatesForQuest;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetQuestOrderablesUseCase {
    private final QuestRepository questRepository;
    private final PersonRepository personRepository;

    /**
     * クエストの発注が可能な冒険者と、発注時の報酬見積
     */
    public GetQuestOrderablesUseCaseResult run(long worldId, long questId) {
        Quest quest = questRepository.get(questId).orElseThrow(IllegalStateException::new);
        List<Adventurer> orderables = personRepository.getOrderables(worldId);
        List<Adventurer> orderedBy =  personRepository.getAdventurers(worldId,
                quest.getQuestOrders().stream().map(QuestOrder::getPersonId).collect(Collectors.toList()));

        return new GetQuestOrderablesUseCaseResult(
                orderables.stream().map(adventurer ->
                        new GetQuestOrderablesUseCaseResult.QuestOrderable(
                                adventurer,
                                new EstimatesForQuest(quest, adventurer, orderedBy),
                                adventurer.calcRewards(quest).getValue()
                        )).collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    public static class GetQuestOrderablesUseCaseResult {
        List<QuestOrderable> questOrderables;

        @AllArgsConstructor
        @Getter
        public static class QuestOrderable {
            private final Adventurer adventurer;
            private final EstimatesForQuest estimatesForQuest;
            private final long rewards;
        }
    }
}
