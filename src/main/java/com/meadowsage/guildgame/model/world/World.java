package com.meadowsage.guildgame.model.world;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class World {
    @Getter
    protected long id;
    @Getter
    protected int gameDate;
    @Getter
    protected State state;

    // "setState"にするとMyBatisが勝手に使ってNPE吐いちゃうので注意
    public void changeState(State state) {
        switch (state) {
            case MORNING:
                if (!this.state.equals(State.MIDNIGHT)) throw new IllegalStateException();
                break;
            case MIDDAY:
                if (!this.state.equals(State.MORNING)) throw new IllegalStateException();
                break;
            case AFTERNOON:
                if (!this.state.equals(State.MIDDAY)) throw new IllegalStateException();
                break;
            case NIGHT:
                if (!this.state.equals(State.AFTERNOON)) throw new IllegalStateException();
                break;
            case MIDNIGHT:
                if (!this.state.equals(State.NIGHT)) throw new IllegalStateException();
                break;
        }
        this.state = state;
    }

    public void toNextDay() {
        gameDate++;
    }

    public enum State {
        MORNING, // 朝（キャラクター行動決定）
        MIDDAY, // 昼（ユーザ操作待ち）
        AFTERNOON, // 午後（キャラクター行動）
        NIGHT, // 夜（結果表示）
        MIDNIGHT // 深夜（ワールド更新）
    }
}
