package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetOngoingQuestsResponse {
    List<ResponseQuest> quests;

    public GetOngoingQuestsResponse(List<Quest> quests, List<Adventurer> adventurers) {
        this.quests = quests.stream().map(quest -> {
            List<ResponseQuestOrder> responseQuestOrders = quest.getQuestOrders().stream()
                    .map(questOrder -> new ResponseQuestOrder(quest, questOrder, adventurers))
                    .collect(Collectors.toList());

            return ResponseQuest.builder()
                    .id(quest.getId())
                    .type(quest.getType().name())
                    .name(quest.getName())
                    .difficulty(quest.getDifficulty())
                    .danger(quest.getDanger())
                    .rewards(quest.getRewards().getValue())
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
        int danger;
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
            this.reservedBy = (target != null) ? ResponseReservedBy.builder()
                    .id(target.getId())
                    .name(target.getName().getFirstName())
                    .fullName(target.getName().getFullName())
                    .battle(target.getBattle().getValue())
                    .knowledge(target.getKnowledge().getValue())
                    .support(target.getSupport().getValue())
                    .energy(target.getEnergy().getValue())
                    .skills(Arrays.asList(
                            ResponseSkill.builder().name("ベテラン").color("green").build(),
                            ResponseSkill.builder().name("不運").color("red").build(),
                            ResponseSkill.builder().name("剣術").color("purple").level(9).build(),
                            ResponseSkill.builder().name("回復魔法").color("grey").level(1).build()
                    ))
                    .rewards(target.calcRewards(quest).getValue())
                    .build() : null;
        }
    }

    @Getter
    @Builder
    private static class ResponseReservedBy {
        long id;
        String name;
        String fullName;
        int battle;
        int knowledge;
        int support;
        int energy;
        List<ResponseSkill> skills;
        long rewards;
    }

    @Getter
    @Builder
    private static class ResponseSkill {
        String name;
        Integer level;
        String color;
    }
}
