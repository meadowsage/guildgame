package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;

import java.util.List;

public class EstimatesForQuest {
    private final Quest quest;
    private final Adventurer adventurer;
    private final int nextCost;
    private final int diffOfPerformanceAndDifficulty;

    public EstimatesForQuest(Quest quest, Adventurer adventurer, List<Adventurer> party) {
        this.quest = quest;
        this.adventurer = adventurer;
        int currentCost = party.stream().mapToInt(partyMember -> partyMember.calcReward(quest)).sum();
        this.nextCost = adventurer.calcReward(quest) + currentCost;
        this.diffOfPerformanceAndDifficulty = 0;
    }

    public boolean isAlright() {
        return !isTired() && !isSmallLackOfSkills() && !isLackOfSkills() && !isNotInterestedIn() && !isCostOverrun();
    }

    public boolean isTired() {
        return adventurer.isTired();
    }

    public boolean isSmallLackOfSkills() {
        return !isLackOfSkills() && diffOfPerformanceAndDifficulty >= 10;
    }

    public boolean isLackOfSkills() {
        return diffOfPerformanceAndDifficulty >= 20;
    }

    public boolean isNotInterestedIn() {
        // TODO 興味なし
        return false;
    }

    public boolean isCostOverrun() {
        return nextCost > quest.getReward().getValue();
    }
}