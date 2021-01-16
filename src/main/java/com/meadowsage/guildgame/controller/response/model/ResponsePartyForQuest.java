package com.meadowsage.guildgame.controller.response.model;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import lombok.Getter;

@Getter
public class ResponsePartyForQuest extends ResponseParty {
    int payments;
    boolean isSatisfyRequirements;
    boolean isSatisfyRecommends;

    public ResponsePartyForQuest(Party party, Quest quest) {
        super(party);
        this.payments = party.calcReward(quest);
        this.isSatisfyRequirements = quest.isSatisfyRequirements(party);
        this.isSatisfyRecommends = quest.isSatisfyRecommends(party);
    }
}
