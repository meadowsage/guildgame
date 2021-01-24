package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponseQuest;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetQuestsResponse {
    List<ResponseQuest> quests;

    public GetQuestsResponse(List<Quest> quests, List<QuestOrder> questOrders, List<Party> parties) {
        this.quests = quests.stream().map(quest -> {
            QuestOrder questOrder = questOrders.stream()
                    .filter(qo -> qo.getQuestId() == quest.getId())
                    .findFirst()
                    .orElse(null);
            Party party = questOrder != null ? parties.stream()
                    .filter(p -> p.getId() == questOrder.getPartyId())
                    .findFirst()
                    .orElse(null) : null;
            return new ResponseQuest(quest, questOrder, party);
        }).collect(Collectors.toList());
    }
}
