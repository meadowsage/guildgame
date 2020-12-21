package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {
    @Getter
    private long id;
    @Getter
    private PersonName name;
    @Getter
    private Money money;
    @Getter
    private Reputation reputation;
    @Getter
    private Attributes attributes;
    @Getter
    private Resource energy;
    @Getter
    private boolean isAdventurer;

    public int getBattle() {
        return attributes.getBattle();
    }

    public int getKnowledge() {
        return attributes.getKnowledge();
    }

    public int getSupport() {
        return attributes.getSupport();
    }

    public boolean isNotSaved() {
        return id == -1;
    }

    public boolean isApplicant() {
        return !isAdventurer;
    }

    public boolean isTired() {
        return this.energy.getValue() == 0;
    }

    public void doDaytimeActivity(World world, GameLogger gameLogger) {
        if(isTired()) {
            // 休息を取る
            gameLogger.add(name.getFirstName() + "は休息をとった。", id);
            energy.recoverToMax();
        } else {
            world.getAvailableQuests().stream().findFirst()
                    .ifPresent(quest -> quest.reserve(this));
        }
    }

    public static List<Person> generateAdventurer(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            Person person = new Person();
            person.id = -1;
            person.name = PersonName.generateRandom();
            person.attributes = Attributes.generateRandom();
            person.money = Money.of((int) (500 + Math.random() * 500));
            person.reputation = Reputation.of((int) (Math.random() * 10));
            int energy = (int) (1 + Math.random() * 4);
            if (person.getBattle() >= 60) energy += 1;
            else if (person.getBattle() <= 30) energy = Math.max(1, energy - 1);
            person.energy = Resource.of(energy);
            person.isAdventurer = true;
            return person;
        }).collect(Collectors.toList());
    }

    public static List<Person> generateApplicant(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            Person person = generateAdventurer(1).get(0);
            person.money = Money.of((int) (250 + Math.random() * 250));
            person.reputation = Reputation.of(0);
            person.isAdventurer = false;
            return person;
        }).collect(Collectors.toList());
    }

    private static Person of(
            String firstName,
            String familyName,
            int battle,
            int knowledge,
            int support,
            int energy,
            int money,
            int reputation,
            boolean isAdventurer
    ) {
        Person person = new Person();
        person.id = -1;
        person.name = PersonName.of(firstName, familyName);
        person.attributes = Attributes.of(battle, knowledge, support);
        person.money = Money.of(money);
        person.reputation = Reputation.of(reputation);
        person.energy = Resource.of(energy);
        person.isAdventurer = isAdventurer;
        return person;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum UniquePerson {
        TELLAN("テラン", "ウォレス", 38, 42, 62, 1, 2000, 10, true); // Tellan Wallace

        private String firstName;
        private String familyName;
        private int battle;
        private int knowledge;
        private int support;
        private int energy;
        private int money;
        private int reputation;
        private boolean isAdventurer;

        public Person getInstance() {
            return of(firstName, familyName, battle, knowledge, support, energy, money, reputation, isAdventurer);
        }
    }
}
