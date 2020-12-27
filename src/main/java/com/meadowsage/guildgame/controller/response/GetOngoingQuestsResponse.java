package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
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
            List<ResponseQuestOrder> questOrders = quest.getQuestOrders().stream()
                    .map(questOrder -> {
                        Adventurer target = adventurers.stream()
                                .filter(adventurer -> adventurer.getId() == questOrder.getPersonId())
                                .findAny().orElse(null);
                        ResponseReservedBy reservedBy = (target != null) ?
                                new ResponseReservedBy(
                                        target.getId(),
                                        target.getName().getFirstName(),
                                        target.calcRewards(quest)
                                ) : null;
                        return new ResponseQuestOrder(questOrder.getId(), reservedBy);
                    }).collect(Collectors.toList());

            return ResponseQuest.builder()
                    .id(quest.getId())
                    .type(quest.getType().name())
                    .name(quest.getName())
                    .difficulty(quest.getDifficulty())
                    .reward(quest.calcRewards())
                    .questOrders(questOrders)
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
        long reward;
        List<ResponseQuestOrder> questOrders;
    }

    @AllArgsConstructor
    @Getter
    static class ResponseQuestOrder {
        long id;
        ResponseReservedBy reservedBy;
    }

    @AllArgsConstructor
    @Getter
    private static class ResponseReservedBy {
        long id;
        String name;
        long reward;
    }
}
