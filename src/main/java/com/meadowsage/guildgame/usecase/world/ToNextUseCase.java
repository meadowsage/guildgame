package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.accounting.Treasurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.TreasurerRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Deprecated
public class ToNextUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;
    private final TreasurerRepository treasurerRepository;

    public ToNextUseCaseResult run(String saveDataId) {
        GameWorld world = worldRepository.getGameWorld(saveDataId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());
        Treasurer treasurer = Treasurer.builder().worldId(world.getId()).gameDate(world.getGameDate()).build();
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
                title = quest.map(value -> "クエスト " + value.getName()).orElse("その他の行動");
                break;
            case NIGHT:
                // 夜→朝
                // 夜の処理（成長）
                world.night(gameLogger, treasurer);
                title = "今日の結果";
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
        gameLogRepository.save(gameLogger);
        treasurerRepository.save(treasurer);
        return new ToNextUseCaseResult(world, gameLogger.getLogs(), title);
    }

    @AllArgsConstructor
    @Getter
    public static class ToNextUseCaseResult {
        GameWorld world;
        List<GameLog> gameLogs;
        String title;
    }
}
