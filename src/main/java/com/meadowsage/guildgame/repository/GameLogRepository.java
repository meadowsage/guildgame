package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GameLogMapper;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameLogRepository {

    private final GameLogMapper gameLogMapper;

    public void save(GameLogger gameLogger) {
        gameLogger.getLogs().forEach(gameLogMapper::insert);
    }

    public List<GameLog> getGameLogs(long worldId, int gameDate) {
        return gameLogMapper.selectAll(worldId, gameDate);
    }

    public List<GameLog> getQuestGameLogs(long worldId, int gameDate, long questId) {
        return gameLogMapper.select(worldId, gameDate, questId, null);
    }

    public List<GameLog> getOtherActionGameLogs(long worldId, int gameDate) {
        return gameLogMapper.select(worldId, gameDate, null, true);
    }
}
