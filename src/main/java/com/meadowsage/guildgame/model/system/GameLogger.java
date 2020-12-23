package com.meadowsage.guildgame.model.system;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
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

    public void add(String message, Person person) {
        logs.add(new GameLog(message, worldId, person.getId(), null, gameDate));
        System.out.println(message); // DEBUG
    }

    public void add(String message, Quest quest) {
        logs.add(new GameLog(message, worldId, null, quest.getId(), gameDate));
        System.out.println(message); // DEBUG
    }
    public void add(String message, Person person, Quest quest) {
        logs.add(new GameLog(message, worldId, person.getId(), quest.getId(), gameDate));
        System.out.println(message); // DEBUG
    }

    public void add(String message) {
        logs.add(new GameLog(message, worldId, null, null, gameDate));
    }
}
