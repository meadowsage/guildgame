package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponseAdventurer;
import com.meadowsage.guildgame.model.person.Party;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetPartiesResponse {
    List<ResponseParty> parties;

    public GetPartiesResponse(List<Party> parties) {
        this.parties = parties.stream().map(party -> ResponseParty.builder()
                .id(party.getId())
                .name(party.getName())
                .members(party.getMembers().stream()
                        .map(ResponseAdventurer::new)
                        .collect(Collectors.toList())
                ).build()
        ).collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class ResponseParty {
        long id;
        String name;
        List<ResponseAdventurer> members;
    }
}
