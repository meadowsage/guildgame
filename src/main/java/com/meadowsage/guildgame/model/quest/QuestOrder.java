package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestOrder {
    @Getter
    private long id;
    private long questId;
    @Getter
    private long personId;
    @Getter
    private State state;

    public QuestOrder(long questId, long personId) {
        this.id = -1;
        this.questId = questId;
        this.personId = personId;
        this.state = State.ONGOING;
    }

    public boolean isNew() {
        return this.id == -1;
    }

    public boolean isActive() {
        return this.state.equals(State.ONGOING);
    }

    void complete() {
        this.state = State.SUCCESS;
    }

    void fail() {
        this.state = State.FAILURE;
    }

    public enum State {
        ONGOING, SUCCESS, FAILURE
    }
}
