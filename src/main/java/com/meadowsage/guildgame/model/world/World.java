package com.meadowsage.guildgame.model.world;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class World {
    @Getter
    @Builder.Default
    protected long id = -1;
    @Getter
    @Builder.Default
    protected int gameDate = 1;
    @Getter
    @Builder.Default
    protected State state = State.MIDDAY;

    public enum State {
        MORNING, // 朝（キャラクター行動決定）
        MIDDAY, // 昼（ユーザ操作待ち）
        AFTERNOON, // 午後（キャラクター行動）
        NIGHT, // 夜（結果表示）
        MIDNIGHT // 深夜（ワールド更新）
    }
}
