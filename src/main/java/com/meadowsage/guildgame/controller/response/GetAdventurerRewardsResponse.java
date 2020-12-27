package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAdventurerRewardsResponse {
    long id;
    String name;
    long money;
    long reputation;
    int battle;
    int knowledge;
    int support;
    int energy;
    int maxEnergy;
    long rewards;

    public GetAdventurerRewardsResponse(Adventurer adventurer, long rewards) {
        this.id = adventurer.getId();
        this.name = adventurer.getName().getFirstName();
        this.money = adventurer.getMoney().getValue();
        this.reputation = adventurer.getReputation().getValue();
        this.battle = adventurer.getBattle().getValue();
        this.knowledge = adventurer.getKnowledge().getValue();
        this.support = adventurer.getSupport().getValue();
        this.energy = adventurer.getEnergy().getValue();
        this.maxEnergy = adventurer.getEnergy().getMax();
        this.rewards = rewards;
    }
}
