package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponsePartyForQuest;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetQuestsResponse {
    List<ResponseQuest> quests;

    public GetQuestsResponse(List<Quest> quests, List<QuestOrder> questOrders, List<Party> parties) {
        this.quests = quests.stream().map(quest -> {
            QuestOrder questOrder = questOrders.stream().filter(qo -> qo.getQuestId() == quest.getId()).findFirst().orElse(null);
            Party party = questOrder != null ? parties.stream().filter(p -> p.getId() == questOrder.getPartyId()).findFirst().orElse(null) : null;
            return new ResponseQuest(quest, questOrder, party);
        }).collect(Collectors.toList());
    }

    @Getter
    static class ResponseQuest {
        long id;
        String name;
        long reward;
        int danger;
        int amount;
        long income;
        List<ResponseRequirement> requirements;
        List<ResponseRequirement> recommends;
        ResponseQuestOrder questOrder;

        public ResponseQuest(Quest quest, QuestOrder questOrder, Party party) {
            this.id = quest.getId();
            this.name = quest.getName();
            this.reward = quest.getReward().getValue();
            this.danger = quest.getDanger();
            this.amount = quest.getAmount();
            this.income = party != null ? this.reward - party.calcReward(quest) : this.reward;
            this.requirements = quest.getRequirements().stream()
                    .map(ResponseRequirement::new).collect(Collectors.toList());
            this.recommends = quest.getRecommends().stream()
                    .map(ResponseRequirement::new).collect(Collectors.toList());
            this.questOrder = questOrder != null ? new ResponseQuestOrder(questOrder, party, quest) : null;
        }
    }

    @Getter
    static class ResponseQuestOrder {
        long id;
        ResponsePartyForQuest party;

        public ResponseQuestOrder(QuestOrder questOrder, Party party, Quest quest) {
            this.id = questOrder.getId();
            this.party = new ResponsePartyForQuest(party, quest);
        }
    }

    @Getter
    static class ResponseRequirement {
        String label;
        int value;

        public ResponseRequirement(QuestContent.Requirement requirement) {
            this.label = requirement.getLabel();
            this.value = requirement.getValue();
        }
    }
}
