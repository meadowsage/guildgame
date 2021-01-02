package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
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
    private int difficulty;
    @Getter
    private Money rewards;
    @Getter
    private int danger;
    @Getter
    private Place place;
    private QuestContent content;
    @Getter
    private int processedAt;
    @Getter
    private boolean isClosed;
    @Getter
    private final List<QuestOrder> questOrders = new ArrayList<>();

    Quest(QuestType type, QuestContent content, int difficulty, int danger, Place place) {
        this.type = type;
        this.difficulty = difficulty;
        this.danger = danger;
        this.place = place;
        this.content = content;
        this.rewards = calcRewards();
    }

    public String getName() {
        return content.getContentName();
    }

    private Money calcRewards() {
        return Money.of((int) (Math.pow(difficulty, 2) * 3 + Math.pow(danger, 2) * 100 + 200));
    }

    /**
     * 現在の収入見込みを算出する
     *
     * @param adventurers 冒険者リスト（パーティメンバを含む）
     * @return 報酬 - 冒険者コスト合計
     */
    public int calcIncome(List<Adventurer> adventurers) {
        int totalCosts = extractPartyMembers(adventurers).stream()
                .mapToInt(partyMember -> partyMember.calcRewards(this).getValue()).sum();
        return getRewards().getValue() - totalCosts;
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
        return processedAt >= currentGameDate || questOrders.stream().noneMatch(QuestOrder::isActive);
    }

    /**
     * クエストが成功したかどうか
     *
     * @return 成功していればtrue
     */
    public boolean isSucceeded() {
        return questOrders.stream().anyMatch(QuestOrder::isSucceeded);
    }

    public void order(Adventurer adventurer) {
        questOrders.add(new QuestOrder(this, adventurer));
    }

    public void markAsSucceeded(int gameDate) {
        this.processedAt = gameDate;
        questOrders.forEach(QuestOrder::markAsSucceeded);
    }

    public void markAsFailed() {
        questOrders.forEach(QuestOrder::markAsFailed);
    }

    public void close() {
        isClosed = true;
    }

    public int criticalEvent(Adventurer adventurer, GameLogger gameLogger) {
        return content.getEvents().criticalEvent(this, adventurer, gameLogger);
    }

    public int specialEvent(Adventurer adventurer, GameLogger gameLogger) {
        return content.getEvents().specialEvent(this, adventurer, gameLogger);
    }
    public int successEvent(Adventurer adventurer, GameLogger gameLogger) {
        return content.getEvents().successEvent(this, adventurer, gameLogger);
    }
    public int failureEvent(Adventurer adventurer, GameLogger gameLogger) {
        return content.getEvents().failureEvent(this, adventurer, gameLogger);
    }
    public int fumbleEvent(Adventurer adventurer, GameLogger gameLogger) {
        return content.getEvents().fumbleEvent(this, adventurer, gameLogger);
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
