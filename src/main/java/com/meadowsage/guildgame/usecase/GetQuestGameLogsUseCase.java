package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.repository.GameLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetQuestGameLogsUseCase {
    private final GameLogRepository gameLogRepository;

    public List<GameLog> run(long worldId, int gameDate, Long questId, Boolean otherActions) {
        if(otherActions) {
            return gameLogRepository.getOtherActionGameLogs(worldId, gameDate);
        } else {
            return gameLogRepository.getQuestGameLogs(worldId, gameDate, questId);
        }
    }
}
