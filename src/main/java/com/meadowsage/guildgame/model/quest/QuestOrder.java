package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.value.Money;
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
    private Money rewards;
    @Getter
    private State state;

    public QuestOrder(Quest quest, Adventurer adventurer) {
        this.id = -1;
        this.questId = quest.getId();
        this.personId = adventurer.getId();
        this.state = State.ONGOING;
        this.rewards = adventurer.calcRewards(quest);
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
