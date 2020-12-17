package com.meadowsage.guildgame.model.system;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameLogger {
    long worldId;
    int gameDate;
    @Getter
    List<GameLog> logs = new ArrayList<>();

    public GameLogger(long worldId, int gameDate) {
        this.worldId = worldId;
        this.gameDate = gameDate;
    }

    public void add(String message, Long personId) {
        logs.add(new GameLog(message, worldId, personId, gameDate));
        System.out.println(message); // DEBUG
    }

    public void add(String message) {
        this.add(message, null);
    }
}
