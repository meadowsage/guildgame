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
            QuestType type = place.decideQuestType(dice);
            QuestContent detail = place.decideQuestDetail(type, dice);
            int difficulty = decideDifficulty(type, place);
            int danger = decideDanger(type);
            return new Quest(type, detail, difficulty, danger, place);
        }).collect(Collectors.toList());
    }

    private Place decidePlace() {
        return places.get(dice.roll(1, places.size()) - 1);
    }

    private int decideDifficulty(QuestType type, Place place) {
        // FIXME 場所に応じて難易度の補正が入る
        int base = 20 + (place.equals(Place.CITY) ? 0 : 20);
        // 補正
        base *= type.getDifficultyCoefficient();
        return base;
    }

    private int decideDanger(QuestType type) {
        // FIXME 場所のベース難易度、討伐対象に応じて値を変える
        if (type.equals(QuestType.TASK) || type.equals(QuestType.HARVEST)) return 0;
        else return 1;
    }
}
