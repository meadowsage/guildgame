package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class World {
    @Getter
    private long id;
    @Getter
    private int gameDate;
    @Getter
    @Setter
    private Guild guild;
    @Getter
    private List<Person> persons = new ArrayList<>();
    @Getter
    private List<Quest> quests = new ArrayList<>();

    public void addPersons(List<Person> persons) {
        this.persons.addAll(persons);
    }

    public void addQuests(List<Quest> quests) {
        this.quests.addAll(quests);
    }

    public void toNextDay() {
        night();
        morning();
    }

    private void night() {
        // クエストの成否判定
        this.quests.forEach(quest -> System.out.println(quest.getType()));

        // 冒険者更新
        this.persons.forEach(person -> System.out.println(person.getName().getFirstName()));

        // ギルド更新
        this.guild.accountingProcess();

        // 日付進める
        this.gameDate++;
    }

    private void morning() {
        // 冒険者がクエストを受注
    }

    public static World create() {
        World world = new World();
        world.id = -1;
        world.gameDate = 1;
        world.guild = Guild.create();
        world.persons = IntStream.range(1, 11).mapToObj(value -> Person.generateRandom()).collect(Collectors.toList());
        world.quests = IntStream.range(1, 6).mapToObj(value -> Quest.generateRandom()).collect(Collectors.toList());
        return world;
    }
}
