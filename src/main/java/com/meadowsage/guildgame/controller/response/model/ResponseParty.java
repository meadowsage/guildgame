package com.meadowsage.guildgame.controller.response.model;

import com.meadowsage.guildgame.model.person.Party;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseParty {
    long id;
    String name;
    long leaderId;
    List<ResponseAdventurer> members;

    public ResponseParty(Party party) {
        this.id = party.getId();
        this.name = party.getName();
        this.leaderId = party.getLeaderId();
        this.members = party.getMembers().stream().map(ResponseAdventurer::new).collect(Collectors.toList());
    }
}
