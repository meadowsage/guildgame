package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.person.PersonSkill;
import com.meadowsage.guildgame.model.person.Personality;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetAdventurersResponse {
    List<ResponseAdventurer> adventurers;

    public GetAdventurersResponse(List<Adventurer> adventurers) {
        this.adventurers = adventurers.stream()
                .sorted(Comparator.comparing(Person::getId))
                .map(person -> ResponseAdventurer.builder()
                        .id(person.getId())
                        .name(person.getName().getFirstName())
                        .fullName(person.getName().getFullName())
                        .money(person.getMoney().getValue())
                        .reputation(person.getReputation().getValue())
                        .battle(person.getBattle().getValue())
                        .knowledge(person.getKnowledge().getValue())
                        .support(person.getSupport().getValue())
                        .energy(person.getEnergy().getValue())
                        .maxEnergy(person.getEnergy().getMax())
                        .personalities(person.getPersonalities().stream()
                                .map(Personality::getLabel)
                                .collect(Collectors.toList()))
                        .skills(person.getSkills().stream()
                                .map(ResponseSkill::new)
                                .collect(Collectors.toList()))
                        .imageBodyFileName(person.getImageBodyFileName())
                        .build()
                ).collect(Collectors.toList());
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
        List<ResponseSkill> skills;
        String imageBodyFileName;
    }

    @Getter
    private static class ResponseSkill {
        String name;
        int level;

        public ResponseSkill(PersonSkill skill) {
            this.name = skill.getSkill().getLabel();
            this.level = skill.getLevel();
        }
    }
}