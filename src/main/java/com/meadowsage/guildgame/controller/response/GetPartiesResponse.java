package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.person.Personality;
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
                .partyMembers(party.getPartyMembers().stream().map(partyMember ->
                        ResponseAdventurer.builder()
                                .id(partyMember.getId())
                                .name(partyMember.getName().getFirstName())
                                .fullName(partyMember.getName().getFullName())
                                .money(partyMember.getMoney().getValue())
                                .reputation(partyMember.getReputation().getValue())
                                .battle(partyMember.getBattle().getValue())
                                .knowledge(partyMember.getKnowledge().getValue())
                                .support(partyMember.getSupport().getValue())
                                .energy(partyMember.getEnergy().getValue())
                                .maxEnergy(partyMember.getEnergy().getMax())
                                .personalities(partyMember.getPersonalities().stream()
                                        .map(Personality::getLabel)
                                        .collect(Collectors.toList()))
                                .imageBodyFileName(partyMember.getImageBodyFileName())
                                .build()
                ).collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class ResponseParty {
        long id;
        String name;
        List<ResponseAdventurer> partyMembers;
    }

    @Builder
    @Getter
    private static class ResponseAdventurer {
        long id;
        String name;
        String fullName;
        long money;
        long reputation;
        int battle;
        int knowledge;
        int support;
        int energy;
        int maxEnergy;
        List<String> personalities;
        String imageBodyFileName;
    }
}
