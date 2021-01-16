package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestGenerator {
    Dice dice;
    int reputation;
    List<Place> places;

    public QuestGenerator(int reputation, List<Place> places, Dice dice) {
        this.reputation = reputation;
        this.places = places;
        this.dice = dice;
    }

    public List<Quest> generate(int number) {
        return IntStream.range(0, number).mapToObj(value -> {
            Place place = decidePlace();
            QuestContent content = place.decideQuestContent( dice);
            return new Quest(content);
        }).collect(Collectors.toList());
    }

    private Place decidePlace() {
        return places.get(dice.roll(1, places.size()) - 1);
    }
}
