package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quest {
    @Getter
    private long id = -1;
    @Getter
    private QuestType type;
    @Getter
    private String name = "";
    @Getter
    private int difficulty;
    @Getter
    private Place place;
    @Getter
    private int processedDate;
    @Getter
    private List<QuestOrder> questOrders = new ArrayList<>();

    Quest(QuestType type, String name, int difficulty, Place place) {
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.place = place;
    }

    /**
     * まだ誰にも受注されていない
     */
    public boolean isNotOrdered() {
        return questOrders.size() == 0;
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
}
