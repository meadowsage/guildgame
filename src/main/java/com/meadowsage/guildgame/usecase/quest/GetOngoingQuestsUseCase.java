package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetOngoingQuestsUseCase {
    private final QuestRepository questRepository;
    private final PersonRepository personRepository;

    /**
     * 進行中のクエストと、受注している冒険者のリストを返します。
     *
     * @param worldId ワールドID
     * @return GetQuestsUseCaseResult
     */
    public GetQuestsUseCaseResult run(long worldId) {
        List<Quest> ongoingQuests = questRepository.getOngoingQuests(worldId);
        List<Adventurer> adventurers = personRepository.getAdventurers(
                worldId,
                ongoingQuests.stream().map(Quest::getQuestOrders)
                        .flatMap(Collection::stream)
                        .map(QuestOrder::getPersonId)
                        .collect(Collectors.toList())
        );
        return new GetQuestsUseCaseResult(ongoingQuests, adventurers);
    }

    @Getter
    @AllArgsConstructor
    public static class GetQuestsUseCaseResult {
        List<Quest> quests;
        List<Adventurer> adventurers;
    }
}
