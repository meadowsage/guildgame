package com.meadowsage.guildgame.usecase.quest;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.repository.PartyRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetQuestsUseCase {
    private final QuestRepository questRepository;
    private final PartyRepository partyRepository;

    public GetQuestsUseCaseResult run(long worldId) {
        List<Quest> quests = questRepository.getQuests(worldId);
        List<QuestOrder> questOrders = questRepository.getActiveQuestOrders(worldId);
        List<Party> parties = questOrders.stream().map(QuestOrder::getPartyId)
                .map(partyRepository::get)
                .map(Optional::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new GetQuestsUseCaseResult(quests, questOrders, parties);
    }

    @Getter
    @AllArgsConstructor
    public static class GetQuestsUseCaseResult {
        List<Quest> quests;
        List<QuestOrder> questOrders;
        List<Party> parties;
    }
}
