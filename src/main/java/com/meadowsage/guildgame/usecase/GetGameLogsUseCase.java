package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.repository.GameLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetGameLogsUseCase {
    private final GameLogRepository gameLogRepository;

    public List<GameLog> run(long worldId, int gameDate, Long questId, Boolean noQuestId, Boolean noPersonId) {
        if (noQuestId != null && noQuestId) {
            // クエストIDがNULL（その他行動）のログのみ取得
            return gameLogRepository.getGameLogsWithQuestIdNull(worldId, gameDate);
        } else if (noPersonId != null && noPersonId) {
            return gameLogRepository.getGameLogsWithPersonIdNull(worldId, gameDate);
        } else if (questId != null) {
            return gameLogRepository.getQuestGameLogs(worldId, gameDate, questId);
        } else {
            return gameLogRepository.getGameLogs(worldId, gameDate);
        }
    }
}
