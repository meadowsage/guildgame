package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int danger = 1;
    @Getter
    private Place place;
    @Getter
    private int processedDate;
    @Getter
    private List<QuestOrder> questOrders = new ArrayList<>();

    Quest(QuestType type, String name, int difficulty, int danger, Place place) {
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.danger = danger;
        this.place = place;
    }

    public long getRewards() {
        return (long) (Math.pow(difficulty, 2) * 3 + Math.pow(danger, 2) * 300);
    }

    /**
     * 現在の収入見込みを算出する
     *
     * @param adventurers 冒険者リスト（パーティメンバを含む）
     * @return 報酬 - 冒険者コスト合計
     */
    public long calcIncome(List<Adventurer> adventurers) {
        return getRewards() - extractPartyMembers(adventurers).stream()
                .mapToLong(partyMember -> partyMember.calcRewards(this)).sum();
    }

    /**
     * 未受注かどうか
     */
    public boolean isNotOrdered() {
        return questOrders.size() == 0;
    }

    /**
     * 新規作成されたインスタンスかどうか
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

    public void order(Person person) {
        questOrders.add(new QuestOrder(id, person.getId()));
    }

    public void complete(int gameDate) {
        this.processedDate = gameDate;
        questOrders.forEach(QuestOrder::complete);
    }

    public void fail() {
        questOrders.forEach(QuestOrder::fail);
    }

    /**
     * 冒険者リストからパーティメンバを抽出する
     *
     * @param adventurers 冒険者リスト（パーティメンバを含む）
     * @return このクエストを受注している冒険者のリスト
     */
    private List<Adventurer> extractPartyMembers(List<Adventurer> adventurers) {
        if (isNotOrdered()) return new ArrayList<>();

        List<Long> memberIds = this.questOrders.stream().map(QuestOrder::getPersonId).collect(Collectors.toList());

        return adventurers.stream().filter(adventurer ->
                memberIds.contains(adventurer.getId())
        ).collect(Collectors.toList());
    }
}
