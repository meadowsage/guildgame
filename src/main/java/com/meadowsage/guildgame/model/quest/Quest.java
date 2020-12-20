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
    public static final Quest FIRST = Quest.of(QuestType.TASK, "街の見回り", 10);
    @Getter
    private long id = -1;
    @Getter
    private QuestType type;
    private String name = "";
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

    public String getName() {
        if(name.isEmpty()) {
            return type.name() + " QUEST " + id;
        } else return name;
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

    public static List<Quest> generateRandom(int number, int baseDifficulty) {
        return IntStream.range(0, number).mapToObj(value -> {
            QuestType type =  QuestType.values()[(int) (Math.random() * QuestType.values().length)];
            int difficulty = (int) (baseDifficulty * 0.5 + Math.random() * baseDifficulty);
            return Quest.of(type,"", difficulty);
        }).collect(Collectors.toList());
    }

    private static Quest of(QuestType type, String name, int difficulty) {
        Quest quest = new Quest();
        quest.name = name;
        quest.type = type;
        quest.difficulty = difficulty;
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
        private final double battleCoefficient;
        @Getter
        private final double knowledgeCoefficient;
        @Getter
        private final double supportCoefficient;
    }
}
