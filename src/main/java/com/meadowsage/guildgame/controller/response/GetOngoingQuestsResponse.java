package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetOngoingQuestsResponse {
    List<ResponseQuest> quests;

    public GetOngoingQuestsResponse(
            List<Quest> quests,
            List<Adventurer> adventurers
    ) {
        this.quests = quests.stream().map(quest -> {
            List<ResponseQuestOrder> responseQuestOrders = quest.getQuestOrders().stream()
                    .map(questOrder -> new ResponseQuestOrder(quest, questOrder, adventurers))
                    .collect(Collectors.toList());

           return ResponseQuest.builder()
                    .id(quest.getId())
                    .type(quest.getType().name())
                    .name(quest.getName())
                    .difficulty(quest.getDifficulty())
                    .rewards(quest.getRewards())
                    .questOrders(responseQuestOrders)
                    .income(quest.calcIncome(adventurers))
                    .build();
        }).collect(Collectors.toList());
    }

    @Builder
    @Getter
    static class ResponseQuest {
        long id;
        String type;
        String name;
        int difficulty;
        long rewards;
        long income;
        List<ResponseQuestOrder> questOrders;
    }

    @Getter
    static class ResponseQuestOrder {
        long id;
        ResponseReservedBy reservedBy;

        public ResponseQuestOrder(Quest quest, QuestOrder questOrder, List<Adventurer> adventurers) {
            this.id = questOrder.getId();

            Adventurer target = adventurers.stream()
                    .filter(adventurer -> adventurer.getId() == questOrder.getPersonId())
                    .findAny().orElse(null);
            this.reservedBy = (target != null) ? new ResponseReservedBy(
                    target.getId(),
                    target.getName().getFirstName(),
                    target.calcRewards(quest)
            ) : null;
        }
    }

    @AllArgsConstructor
    @Getter
    private static class ResponseReservedBy {
        long id;
        String name;
        long reward;
    }
}
