package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
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
    private final List<QuestOrderProgress> questOrderProgresses = new ArrayList<>();
    @Nullable
    private QuestOrderProgress newProgress;
    @Nullable
    private QuestOrderResult questOrderResult;

    public QuestOrder(long questId, long partyId) {
        this.id = -1;
        this.questId = questId;
        this.partyId = partyId;
    }

    public boolean isNew() {
        return this.id == -1;
    }

    public boolean isOngoing() {
        return !questOrderProgresses.isEmpty();
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
        return questOrderResult != null && questOrderResult.isSucceeded();
    }

    public int getTotalSuccessPoint() {
        return questOrderProgresses.stream().mapToInt(QuestOrderProgress::getProgress).sum();
    }

    public Optional<QuestOrderProgress> getNewProgress() {
        return Optional.ofNullable(newProgress);
    }

    public Optional<QuestOrderResult> getResult() {
        return Optional.ofNullable(questOrderResult);
    }

    public enum State {
        ONGOING, SUCCESS, FAILURE
    }
}
