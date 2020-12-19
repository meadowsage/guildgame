package com.meadowsage.guildgame.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
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
    @JsonIgnore
    private Attributes attributes;
    @Getter
    private Money money;
    @Getter
    private int reputation;
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

        if (this.getKnowledge() <= 40 && this.getSupport() <= 40 && this.getBattle() > 60) {
            remarks.add("筋力自慢という印象だ。");
        } else if(this.getBattle() >= 60) {
            remarks.add("モンスターにも果敢に立ち向かってくれそうだ。");
        }

        if (this.getBattle() <= 40 && this.getSupport() <= 40 && this.getKnowledge() > 60) {
            remarks.add("学者気質という印象だ。");
        } else if(this.getKnowledge() > 60) {
            remarks.add("彼の知恵はきっと役に立つだろう。");
        }

        if (this.getBattle() <= 40 && this.getKnowledge() <= 40 && this.getSupport() > 60) {
            remarks.add("地味だが仕事はできるようだ。");
        } else if(this.getSupport() > 60) {
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
            person.money = Money.of((int) (Math.random() * 1000));
            person.reputation = (int) (Math.random() * 10);
            person.isAdventurer = true;
            return person;
        }).collect(Collectors.toList());
    }

    public static List<Person> generateApplicant(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            Person person = new Person();
            person.id = -1;
            person.name = PersonName.generateRandom();
            person.attributes = Attributes.generateRandom();
            person.money = Money.of((int) (Math.random() * 500));
            person.reputation = 0;
            person.isAdventurer = false;
            return person;
        }).collect(Collectors.toList());
    }
}
