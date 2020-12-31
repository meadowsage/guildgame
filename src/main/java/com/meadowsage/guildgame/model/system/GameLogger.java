package com.meadowsage.guildgame.model.system;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import lombok.Getter;
import org.springframework.lang.Nullable;

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

    public void fatal(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.FATAL);
    }

    public void fatal(String message) {
        log(message, null, null, GameLog.LogLevel.FATAL);
    }

    public void warning(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.WARNING);
    }

    public void warning(String message) {
        log(message, null, null, GameLog.LogLevel.WARNING);
    }

    public void important(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.IMPORTANT);
    }

    public void important(String message) {
        log(message, null, null, GameLog.LogLevel.IMPORTANT);
    }

    public void info(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.INFO);
    }

    public void info(String message) {
        log(message, null, null, GameLog.LogLevel.INFO);
    }

    public void detail(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.DETAIL);
    }

    public void detail(String message) {
        log(message, null, null, GameLog.LogLevel.DETAIL);
    }

    public void debug(String message, Person person, Quest quest) {
        log(message, person, quest, GameLog.LogLevel.DEBUG);
    }

    public void debug(String message) {
        log(message, null, null, GameLog.LogLevel.DEBUG);
    }

    private void log(String message, @Nullable Person person, @Nullable Quest quest, GameLog.LogLevel logLevel) {
        logs.add(new GameLog(message, worldId,
                person != null ? person.getId() : null,
                quest != null ? quest.getId() : null,
                gameDate,
                logLevel));
    }
}
