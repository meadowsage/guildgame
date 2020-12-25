package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ToNextUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;

    public ToNextUseCaseResult run(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        switch (world.getState()) {
            case MIDDAY:
            case AFTERNOON:
                // 午後の処理（キャラクター行動）
                world.afternoon(gameLogger);
                break;
            case NIGHT:
                // 夜→朝
                // 夜の処理（成長）
                world.night(gameLogger);
                break;
            case MIDNIGHT:
                // Worldリソース生成
                // IDが付与されていないリソースができるので注意！
                world.midnight();
                break;
            case MORNING:
                // 朝の処理
                world.morning();
                break;
            default:
                throw new IllegalStateException();
        }

        worldRepository.save(world);
        gameLogRepository.saveGameLog(gameLogger);
        return new ToNextUseCaseResult(world, gameLogger.getLogs());
    }

    @Data
    public static class ToNextUseCaseResult {
        World world;
        List<GameLog> gameLogs;

        public ToNextUseCaseResult(World world, List<GameLog> gameLogs) {
            this.world = world;
            this.gameLogs = gameLogs;
        }
    }
}
