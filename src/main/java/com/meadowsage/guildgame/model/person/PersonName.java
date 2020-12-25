package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonName {
    @Getter
    private String firstName;
    @Getter
    private String familyName;

    public String getFullName() {
        return firstName + "・" + familyName;
    }

    public static PersonName generateRandom(PersonNameGenerator generator) {
        PersonName personName = new PersonName();
        personName.firstName = generator.generate();
        personName.familyName = generator.generate();
        return personName;
    }

    public static PersonName of(String firstName, String familyName) {
        PersonName personName = new PersonName();
        personName.firstName = firstName;
        personName.familyName = familyName;
        return personName;
    }
}