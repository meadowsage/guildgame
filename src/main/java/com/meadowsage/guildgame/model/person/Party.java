package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Party {
    private static final int MAX_LIMIT_OF_MEMBERS = 4;

    @Getter
    long id;
    @Getter
    String name;
    @Getter
    List<Adventurer> partyMembers = new ArrayList<>();

    public boolean canAddMember() {
        return partyMembers.size() < MAX_LIMIT_OF_MEMBERS;
    }

    public boolean isNew() {
        return id == -1;
    }

    public static Party createNew(String name, List<Adventurer> partyMembers) {
        Party party = new Party();
        party.id = -1;
        party.name = name;
        party.partyMembers = partyMembers;
        return party;
    }
}
