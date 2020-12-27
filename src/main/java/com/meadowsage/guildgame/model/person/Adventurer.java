package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.value.Attribute;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Adventurer extends Person {
    protected Adventurer(
            PersonName name,
            Attribute battle,
            Attribute knowledge,
            Attribute support,
            Resource energy,
            Money money,
            Reputation reputation
    ) {
        super(name, battle, knowledge, support, energy, money, reputation);
    }

    @Override
    public boolean isAdventurer() {
        return true;
    }

    public long calcRewards(Quest quest) {
        double base = 100 + getReputation().getValue() * 2;
        return Math.round(base * (quest.getDifficulty() * 0.1) + (quest.getDanger() * 100));
    }
}
