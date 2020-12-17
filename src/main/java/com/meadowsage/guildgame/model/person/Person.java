package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
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
    private Attributes attributes;
    @Getter
    private Money money;
    @Getter
    private int reputation;

    public int getBattle() {
        return attributes.getBattle();
    }

    public int getKnowledge() {
        return attributes.getKnowledge();
    }

    public int getSupport() {
        return attributes.getSupport();
    }

    public static Person generateRandom() {
        Person person = new Person();
        person.id = -1;
        person.name = PersonName.generateRandom();
        person.attributes = Attributes.generateRandom();
        person.money = Money.of(1000);
        person.reputation = 10;
        return person;
    }

    public boolean isNotSaved() {
        return id == -1;
    }

    public static List<Person> generateRandom(int number) {
        return IntStream.range(0, number).mapToObj(value -> generateRandom()).collect(Collectors.toList());
    }
}
