package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.quest.Quest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Party {
    private static final int MAX_LIMIT_OF_MEMBERS = 4;

    @Getter
    long id;
    @Getter
    String name;
    @Getter
    List<Adventurer> members = new ArrayList<>();

    public boolean canAddMember() {
        return members.size() < MAX_LIMIT_OF_MEMBERS;
    }

    public boolean isNew() {
        return id == -1;
    }

    public static Party createNew(String name, List<Adventurer> partyMembers) {
        Party party = new Party();
        party.id = -1;
        party.name = name;
        party.members = partyMembers;
        return party;
    }

    public int calcReward(Quest quest) {
        return members.stream().mapToInt(adventurer -> adventurer.calcReward(quest)).sum();
    }

    public int getMaxAttributeValue(Attribute.Type type) {
        return members.stream().mapToInt(member -> member.getAttribute(type).getValue()).max().orElse(0);
    }

    public int getMaxSkillLevel(Skill skill) {
        return members.stream().map(member -> member.getSkill(skill).orElse(null))
                .filter(Objects::nonNull)
                .mapToInt(PersonSkill::getLevel)
                .max().orElse(0);
    }
}
