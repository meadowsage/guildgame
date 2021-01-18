package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quest {
    @Getter
    private long id = -1;
    private QuestContent content;

    public Quest(QuestContent content) {
        this.content = content;
    }

    public String getName() {
        return content.getContentName();
    }

    public Money getReward() {
        return Money.of(content.getReward());
    }

    public int getDanger() {
        return content.getDanger();
    }

    public int getAmount() {
        return content.getAmount();
    }

    public List<QuestContent.Requirement> getRequirements() {
        return content.getRequirements();
    }

    public List<QuestContent.Requirement> getRecommends() {
        return content.getRecommends();
    }

    public int calcReputation() {
        return getReward().getValue() / 100;
    }

    public boolean isNew() {
        return id == -1;
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
     * 満たされていない必須条件の数
     *
     * @param party パーティ
     */
    public int numberOfUnsatisfiedRequirements(Party party) {
        return (int) content.getRequirements().stream()
                .filter(requirement -> !requirement.isSatisfied(party))
                .count();
    }

    /**
     * 満たされている推奨条件の数
     *
     * @param party パーティ
     */
    public int numberOfSatisfiedRecommends(Party party) {
        return (int) content.getRecommends().stream()
                .filter(recommend -> recommend.isSatisfied(party))
                .count();
    }

    /**
     * 満たされている推奨条件の数
     *
     * @param adventurer 冒険者
     */
    public int numberOfSatisfiedRecommends(Adventurer adventurer) {
        return (int) content.getRecommends().stream()
                .filter(recommend -> recommend.isSatisfied(adventurer))
                .count();
    }

    /**
     * 必須条件を満たしているか
     *
     * @param party パーティ
     * @return 全て満たしていればtrue
     */
    public boolean isSatisfyRequirements(Party party) {
        return numberOfUnsatisfiedRequirements(party) == 0;
    }

    /**
     * 推奨条件を満たしているか
     *
     * @param party パーティ
     * @return **１つでも満たしていれば** true
     */
    public boolean isSatisfyRecommends(Party party) {
        return getRecommends().size() == 0 || numberOfSatisfiedRecommends(party) > 0;
    }
}
