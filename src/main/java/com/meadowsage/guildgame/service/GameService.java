package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final WorldRepository worldRepository;

    public SaveData startNewGame() {
        // 初期化処理
        SaveData saveData = SaveData.create();
        World world = World.create();

        // 初日の処理
        world.toNextDay();

        // 永続化
        gameRepository.createNewSaveData(saveData);
        worldRepository.create(world, saveData.getId());
        return saveData;
    }
}
