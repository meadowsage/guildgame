package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quest {
    @Getter
    private long id = -1;
    @Getter
    private QuestType type;
    private String name = "";
    @Getter
    private int difficulty;
    @Getter
    private List<Long> reservedBy = new ArrayList<>();
    @Getter
    private boolean isCompleted = false;

    public boolean isReserved() {
        return !reservedBy.isEmpty() || isCompleted();
    }

    public boolean isNotSaved() {
        return id == -1;
    }

    public String getName() {
        if (name.isEmpty()) {
            return type.name() + " QUEST " + id;
        } else return name;
    }

    public void reserve(Person person) {
        reservedBy.add(person.getId());
    }

    public void complete() {
        isCompleted = true;
    }

    public void cancel() {
        reservedBy.clear();
    }

    public static List<Quest> generateRandom(int number, int baseDifficulty) {
        return IntStream.range(0, number).mapToObj(value -> {
            QuestType type = QuestType.values()[(int) (Math.random() * QuestType.values().length)];
            int difficulty = (int) (baseDifficulty * 0.5 + Math.random() * baseDifficulty);
            return Quest.of(type, "", difficulty);
        }).collect(Collectors.toList());
    }

    private static Quest of(QuestType type, String name, int difficulty) {
        Quest quest = new Quest();
        quest.name = name;
        quest.type = type;
        quest.difficulty = difficulty;
        return quest;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum UniqueQuest {
        FIRST(QuestType.TASK, "街の見回り", 10);

        QuestType type;
        String name;
        int difficulty;

        public Quest getInstance() {
            return Quest.of(type, name, difficulty);
        }
    }
}
