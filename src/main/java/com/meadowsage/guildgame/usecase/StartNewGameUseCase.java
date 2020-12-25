package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.GameRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 時間を進める処理
 */
@Service
@AllArgsConstructor
public class StartNewGameUseCase {
    private final WorldRepository worldRepository;
    private final GameRepository gameRepository;
    private final GameLogRepository gameLogRepository;

    public SaveData run() {
        SaveData saveData = createNewSaveData();
        generateNewWorld(saveData);
        return saveData;
    }

    private SaveData createNewSaveData() {
        SaveData saveData = SaveData.create();
        gameRepository.createNewSaveData(saveData);
        return saveData;
    }

    private void generateNewWorld(SaveData saveData) {
        // 生成・初期化
        World world = World.generateAndInit(saveData, worldRepository);

        // 初期表示用のログ生成
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate() - 1);
        gameLogger.add("冒険者ギルドへようこそ！");
        gameLogger.add("「冒険者」メニューでは、ギルドに所属する冒険者の情報を確認できます");
        gameLogger.add("「クエスト」メニューでは、クエスト受注状態を編集ができます");
        gameLogger.add("行動が終わったら、右上のボタンで日付を進めてみましょう");
        gameLogRepository.saveGameLog(gameLogger);

        // 保存
        worldRepository.save(world);
    }
}