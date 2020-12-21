package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ゲームシステム周りの処理
 * ワールドデータには変更を加えない
 */
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public SaveData createNewSaveData() {
        SaveData saveData = SaveData.create();
        gameRepository.createNewSaveData(saveData);
        return saveData;
    }

    public List<GameLog> getGameLogs(long worldId, int gameDate) {
        return gameRepository.getGameLogs(worldId, gameDate);
    }
}
