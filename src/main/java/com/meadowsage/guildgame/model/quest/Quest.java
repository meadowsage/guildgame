package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
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
        return reservedBy != null;
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

    public static List<Quest> generate(int number, int reputation) {
        return IntStream.range(0, number).mapToObj(value -> _generate(reputation / 10)).collect(Collectors.toList());
    }

    private static Quest _generate(int max) {
        Quest quest = new Quest();
        quest.id = -1;
        quest.type = QuestType.values()[(int) (Math.random() * QuestType.values().length)];
        quest.difficulty = (int) (Math.random() * max);
        return quest;
    }

    public enum QuestType {
        EXPLORE,
        HUNT,
        HARVEST,
        CHORE;
    }
}
