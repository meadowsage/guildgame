package com.meadowsage.guildgame.model.person;

public class Person {
    private PersonName name;
    private Attributes attributes;

    public static Person generateRandom() {
        Person person = new Person();
        person.name = PersonName.generateRandom();
        person.attributes = Attributes.generateRandom();
        return person;
    }
}
