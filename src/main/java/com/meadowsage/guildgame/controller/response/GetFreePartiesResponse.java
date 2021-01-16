package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponsePartyForQuest;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetFreePartiesResponse {
    List<ResponsePartyForQuest> parties;

    public GetFreePartiesResponse(List<Party> parties, Quest quest) {
        this.parties = parties.stream()
                .map(party -> new ResponsePartyForQuest(party, quest))
                .collect(Collectors.toList());
    }
}
