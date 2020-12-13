package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class World {
    private int date;
    private List<Person> persons;
    private List<Quest> quests;

    public static World create() {
        World world = new World();
        world.date = 1;
        world.persons = IntStream.range(1, 10).mapToObj(value -> Person.generateRandom()).collect(Collectors.toList());
        world.quests = IntStream.range(1, 5).mapToObj(value -> Quest.generateRandom()).collect(Collectors.toList());
        return world;
    }
}
