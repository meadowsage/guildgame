package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.EstimatesForQuest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetQuestOrderablesResponse {
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
    String imageBodyFileName;
    boolean isAlright;
    boolean isTired;
    boolean isSmallLackOfSkills;
    boolean isLackOfSkills;
    boolean isNotInterestedIn;
    boolean isCostOverrun;

    public GetQuestOrderablesResponse(Adventurer adventurer, EstimatesForQuest estimatesForQuest, long rewards) {
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
        this.imageBodyFileName = adventurer.getImageBodyFileName();
        this.isAlright = estimatesForQuest.isAlright();
        this.isTired = estimatesForQuest.isTired();
        this.isSmallLackOfSkills = estimatesForQuest.isSmallLackOfSkills();
        this.isLackOfSkills = estimatesForQuest.isLackOfSkills();
        this.isNotInterestedIn = estimatesForQuest.isNotInterestedIn();
        this.isCostOverrun = estimatesForQuest.isCostOverrun();
    }
}
