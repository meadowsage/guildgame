package com.meadowsage.guildgame.model.system;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameLog {
    @Getter
    Long worldId;
    @Getter
    Long personId;
    @Getter
    Long questId;
    @Getter
    String message;
    @Getter
    int gameDate;

    public GameLog(String message, Long worldId, Long personId, Long questId, int gameDate) {
        this.message = message;
        this.worldId = worldId;
        this.personId = personId;
        this.questId = questId;
        this.gameDate = gameDate;
    }
}
