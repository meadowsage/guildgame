package com.meadowsage.guildgame.controller.response.model;

import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseQuest {
    long id;
    String name;
    long reward;
    int danger;
    int amount;
    int progress;
    long income;
    ResponsePlace place;
    List<ResponseRequirement> requirements;
    List<ResponseRequirement> recommends;
    ResponseQuestOrder questOrder; // 現在有効な受注

    public ResponseQuest(Quest quest, QuestOrder questOrder, Party party) {
        this.id = quest.getId();
        this.name = quest.getName();
        this.reward = quest.getReward().getValue();
        this.danger = quest.getDanger();
        this.amount = quest.getAmount();
        this.progress = quest.getProgress();
        this.income = party != null ? this.reward - party.calcReward(quest) : this.reward;
        this.place = new ResponsePlace(quest.getPlace());
        this.requirements = quest.getRequirements().stream()
                .map(ResponseRequirement::new).collect(Collectors.toList());
        this.recommends = quest.getRecommends().stream()
                .map(ResponseRequirement::new).collect(Collectors.toList());
        this.questOrder = questOrder != null ? new ResponseQuestOrder(questOrder, party, quest) : null;
    }

    @Getter
    static class ResponseQuestOrder {
        long id;
        boolean isOngoing;
        ResponsePartyForQuest party;

        public ResponseQuestOrder(QuestOrder questOrder, Party party, Quest quest) {
            this.id = questOrder.getId();
            this.isOngoing = questOrder.isOngoing();
            this.party = new ResponsePartyForQuest(party, quest);
        }
    }

    @Getter
    static class ResponsePlace {
        String id;
        String name;

        public ResponsePlace(Place place) {
            this.id = place.name();
            this.name = place.getPlaceName();
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