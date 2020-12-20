package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.value.Energy;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private Energy energy;
    @Getter
    private int maxEnergy;
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

    public List<String> createRemarks() {
        List<String> remarks = new ArrayList<>();
        if (this.getBattle() <= 30 && this.getKnowledge() <= 30 && this.getSupport() <= 30) {
            remarks.add("冒険者に向いているとは思えない。");
            return remarks;
        }

        if (this.getBattle() > 60 && this.getKnowledge() > 60 && this.getSupport() > 60) {
            remarks.add("何でもこなせそうだ。");
        }

        if (this.getMaxEnergy() <= 1) {
            remarks.add("あまり活気がない。");
        } else if (this.getMaxEnergy() >= 4) {
            remarks.add("活発だ。");
        }

        if (this.getKnowledge() <= 40 && this.getSupport() <= 40 && this.getBattle() > 60) {
            remarks.add("筋力自慢という印象だ。");
        } else if (this.getBattle() >= 60) {
            remarks.add("モンスターにも果敢に立ち向かってくれそうだ。");
        }

        if (this.getBattle() <= 40 && this.getSupport() <= 40 && this.getKnowledge() > 60) {
            remarks.add("学者気質という印象だ。");
        } else if (this.getKnowledge() > 60) {
            remarks.add("彼の知恵はきっと役に立つだろう。");
        }

        if (this.getBattle() <= 40 && this.getKnowledge() <= 40 && this.getSupport() > 60) {
            remarks.add("地味だが仕事はできるようだ。");
        } else if (this.getSupport() > 60) {
            remarks.add("冒険者の心得をよく理解している。");
        }

        if (remarks.size() == 0) {
            remarks.add("これといった特徴はない。");
        }

        return remarks;
    }

    public static List<Person> generateAdventurer(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            Person person = new Person();
            person.id = -1;
            person.name = PersonName.generateRandom();
            person.attributes = Attributes.generateRandom();
            person.money = Money.of((int) (500 + Math.random() * 500));
            person.reputation = Reputation.of((int) (Math.random() * 10));
            person.maxEnergy = (int) (1 + Math.random() * 4);
            if (person.getBattle() >= 60) person.maxEnergy += 1;
            else if (person.getBattle() <= 30) person.maxEnergy = Math.max(1, person.maxEnergy - 1);
            person.energy = Energy.of(person.maxEnergy);
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
        person.maxEnergy = energy;
        person.energy = Energy.of(person.maxEnergy);
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
