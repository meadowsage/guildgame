package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponseParty;
import com.meadowsage.guildgame.model.person.Party;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetPartiesResponse {
    List<ResponseParty> parties;

    public GetPartiesResponse(List<Party> parties) {
        this.parties = parties.stream().map(ResponseParty::new).collect(Collectors.toList());
    }
}
