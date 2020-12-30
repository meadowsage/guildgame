package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Attribute;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Applicant extends Person {

    protected Applicant(
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

    @Override
    public void doDaytimeActivity(GameWorld world, GameLogger gameLogger) {
        // 何もしない
    }

    @Override
    public void doMorningActivity(GameWorld world) {
        // 何もしない
    }

    public static List<Applicant> generate(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            PersonName name = PersonName.generateRandom(new PersonNameGenerator());
            Attribute battle = Attribute.generateRandom();
            Attribute knowledge = Attribute.generateRandom();
            Attribute support = Attribute.generateRandom();
            Money money = Money.of((int) (500 + Math.random() * 500));
            Reputation reputation = Reputation.of((int) (Math.random() * 10));

            // 戦闘力に応じて体力を調整
            int energyValue = (int) (1 + Math.random() * 4);
            if (battle.getValue() >= 60) energyValue += 1;
            else if (battle.getValue() <= 30) energyValue = Math.max(1, energyValue - 1);
            Resource energy = Resource.of(energyValue);

            return new Applicant(name, battle, knowledge, support, energy, money, reputation);
        }).collect(Collectors.toList());
    }
}
