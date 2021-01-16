package com.meadowsage.guildgame.controller.response.model;


import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.PersonSkill;
import com.meadowsage.guildgame.model.person.Personality;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseAdventurer {
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

    public ResponseAdventurer(Adventurer adventurer) {
        this.id = adventurer.getId();
        this.name = adventurer.getName().getFirstName();
        this.fullName = adventurer.getName().getFullName();
        this.money = adventurer.getMoney().getValue();
        this.reputation = adventurer.getReputation().getValue();
        this.battle = adventurer.getBattle().getValue();
        this.knowledge = adventurer.getKnowledge().getValue();
        this.support = adventurer.getSupport().getValue();
        this.energy = adventurer.getEnergy().getValue();
        this.maxEnergy = adventurer.getEnergy().getMax();
        this.personalities = adventurer.getPersonalities().stream()
                .map(Personality::getLabel)
                .collect(Collectors.toList());
        this.skills = adventurer.getSkills().stream()
                .map(ResponseSkill::new)
                .collect(Collectors.toList());
        this.imageBodyFileName = adventurer.getImageBodyFileName();
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