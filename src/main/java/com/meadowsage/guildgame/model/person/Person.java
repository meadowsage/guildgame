package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {
    @Getter
    private long id;
    @Getter
    private PersonName name;
    @Getter
    private Attributes attributes;
    @Getter
    private int money;
    @Getter
    private int reputation;

    public static Person generateRandom() {
        Person person = new Person();
        person.name = PersonName.generateRandom();
        person.attributes = Attributes.generateRandom();
        person.money = 1000;
        person.reputation = 10;
        return person;
    }
}
