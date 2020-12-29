package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToNextUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;

    public ToNextUseCaseResult run(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());
        String title = "";

        switch (world.getState()) {
            case MIDDAY:
                // 日中の処理
                // ステータスを変えるだけなので、そのまま午後の処理を行う
                world.midday();
            case AFTERNOON:
                // 午後の処理（キャラクターの行動）
                // クエスト実行時はクエスト名をタイトルにする
                Optional<Quest> quest = world.afternoon(gameLogger);
                if(quest.isPresent()) title = quest.get().getName();
                else title = "その他の行動";
                break;
            case NIGHT:
                // 夜→朝
                // 夜の処理（成長）
                world.night(gameLogger);
                title = "１日の結果";
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
        return new ToNextUseCaseResult(world, gameLogger.getLogs(), title);
    }

    @AllArgsConstructor
    @Getter
    public static class ToNextUseCaseResult {
        World world;
        List<GameLog> gameLogs;
        String title;
    }
}
