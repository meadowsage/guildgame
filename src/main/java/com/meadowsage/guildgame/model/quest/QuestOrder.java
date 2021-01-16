package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestOrder {
    @Getter
    private long id;
    @Getter
    private long questId;
    @Getter
    private long partyId;
    private List<QuestOrderProgress> questOrderProgresses;
    @Getter
    @Nullable
    private QuestOrderProgress newProgress;
    @Nullable
    private QuestOrderResult questOrderResult;

    public List<QuestOrderProgress> getQuestOrderProgresses() {
        return Collections.unmodifiableList(questOrderProgresses);
    }

    public Optional<QuestOrderResult> getQuestOrderResult() {
        return Optional.ofNullable(questOrderResult);
    }

    public QuestOrder(long questId, long partyId) {
        this.id = -1;
        this.questId = questId;
        this.partyId = partyId;
    }

    public boolean isNew() {
        return this.id == -1;
    }

    /**
     * クエストが実行済かどうか
     *
     * @param gameDate 現在のゲーム日付
     * @return 実行済ならtrue
     */
    public boolean hasProcessed(int gameDate) {
        return questOrderProgresses.stream()
                .anyMatch(questOrderProgress -> questOrderProgress.getGameDate() == gameDate);
    }

    public boolean isActive() {
        return true;
//        return this.state.equals(State.ONGOING);
    }

    public void markAsSuccess(int gameDate) {
        this.questOrderResult = QuestOrderResult.success(gameDate);
    }

    public void markAsFailure(int gameDate) {
        this.questOrderResult = QuestOrderResult.failure(gameDate);
    }

    public void saveProgress(int gameDate, int progress) {
        this.newProgress = new QuestOrderProgress(gameDate, progress);
    }

    public boolean isSucceeded() {
        return false;
//        return this.state.equals(State.SUCCESS);
    }

    public int getTotalSuccessPoint() {
        return questOrderProgresses.stream().mapToInt(QuestOrderProgress::getProgress).sum();
    }

    public enum State {
        ONGOING, SUCCESS, FAILURE
    }
}
