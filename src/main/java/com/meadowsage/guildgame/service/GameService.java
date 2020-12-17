package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final WorldRepository worldRepository;

    public SaveData startNewGame() {
        // 初期化処理
        SaveData saveData = SaveData.create();
        World world = World.create();
        // 作成
        gameRepository.createNewSaveData(saveData);
        worldRepository.create(world, saveData.getId());
        // 初日の処理
        world.morning();
        // 更新
        worldRepository.save(world);
        return saveData;
    }

    public List<GameLog> getGameLogs(long worldId, int gameDate) {
        return gameRepository.getGameLogs(worldId, gameDate);
    }
}
