package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quest {
    @Getter
    private long id;
    @Getter
    private QuestType type;
    @Getter
    private int difficulty;
    @Nullable
    private Long reservedBy;
    @Getter
    private boolean isCompleted = false;

    public long getReservedBy() {
        return reservedBy != null ? reservedBy : -1;
    }

    public boolean isReserved() {
        return reservedBy != null || isCompleted();
    }

    public boolean isNotSaved() {
        return id == -1;
    }

    public void reserve(Person person) {
        reservedBy = person.getId();
    }

    public void complete() {
        isCompleted = true;
    }

    public void cancel() {
        reservedBy = null;
    }

    public static List<Quest> generate(int number) {
        return IntStream.range(0, number).mapToObj(value -> _generate(100)).collect(Collectors.toList());
    }

    public static List<Quest> generate(int number, int baseDifficulty) {
        return IntStream.range(0, number).mapToObj(value -> _generate(baseDifficulty)).collect(Collectors.toList());
    }

    private static Quest _generate(int baseDifficulty) {
        Quest quest = new Quest();
        quest.id = -1;
        quest.type = QuestType.values()[(int) (Math.random() * QuestType.values().length)];
        quest.difficulty = (int)(baseDifficulty * 0.5 + Math.random() * baseDifficulty);
        return quest;
    }

    @AllArgsConstructor
    public enum QuestType {
        // 探索
        EXPLORE(1.0, 1.0, 1.0),
        // 狩猟
        HUNT(1.8, 0.8, 0.4),
        // 採取
        HARVEST(0.4, 1.8, 1.8),
        // 雑務
        TASK(0.1, 0.4, 2.5);

        @Getter
        private double battleCoefficient;
        @Getter
        private double knowledgeCoefficient;
        @Getter
        private double supportCoefficient;
    }
}
