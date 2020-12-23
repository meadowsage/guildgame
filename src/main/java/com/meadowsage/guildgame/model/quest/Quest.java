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
    private List<QuestOrder> questOrders = new ArrayList<>();
    @Getter
    private int processedDate;

    private Quest(QuestType type, String name, int difficulty) {
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
    }

    public String getName() {
        if (name.isEmpty()) {
            return type.name() + " QUEST " + id;
        } else return name;
    }

    /**
     * 他の冒険者によって予約済
     */
    public boolean isReserved() {
        return questOrders.size() > 0;
    }

    /**
     * 新規作成されたインスタンスかどうか
     *
     * @return ID未付与ならtrue
     */
    public boolean isNew() {
        return id == -1;
    }

    /**
     * クエストが実行済かどうか
     *
     * @param currentGameDate 今日のゲーム日付
     * @return 実行済ならtrue
     */
    public boolean hasProcessed(int currentGameDate) {
        return processedDate >= currentGameDate || questOrders.stream().noneMatch(QuestOrder::isActive);
    }

    public void reserve(Person person) {
        questOrders.add(new QuestOrder(id, person.getId()));
    }

    public void complete(int gameDate) {
        this.processedDate = gameDate;
        questOrders.forEach(QuestOrder::complete);
    }

    public void fail() {
        questOrders.forEach(QuestOrder::fail);
    }

    public void cancel() {
        questOrders.clear();
    }

    public static List<Quest> generateRandom(int number, int baseDifficulty) {
        return IntStream.range(0, number).mapToObj(value -> {
            QuestType type = QuestType.values()[(int) (Math.random() * QuestType.values().length)];
            int difficulty = (int) (baseDifficulty * 0.5 + Math.random() * baseDifficulty);
            return new Quest(type, "", difficulty);
        }).collect(Collectors.toList());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum UniqueQuest {
        FIRST(QuestType.TASK, "街の見回り", 10);

        QuestType type;
        String name;
        int difficulty;

        public Quest getInstance() {
            return new Quest(type, name, difficulty);
        }
    }
}
