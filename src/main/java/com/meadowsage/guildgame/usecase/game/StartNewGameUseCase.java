package com.meadowsage.guildgame.usecase.game;

import com.meadowsage.guildgame.model.accounting.AccountingLogger;
import com.meadowsage.guildgame.model.accounting.GuildBalance;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StartNewGameUseCase {
    private final WorldRepository worldRepository;
    private final GameRepository gameRepository;
    private final GameLogRepository gameLogRepository;
    private final QuestRepository questRepository;
    private final AccountingRepository accountingRepository;

    public SaveData run() {
        SaveData saveData = gameRepository.createNewSaveData();
        generateNewWorld(saveData);
        return saveData;
    }

    private void generateNewWorld(SaveData saveData) {
        // 生成・初期化
        GameWorld world = GameWorld.generateAndInit(saveData, worldRepository, questRepository);
        AccountingLogger accountingLogger = new AccountingLogger(world.getId(), world.getGameDate());

        // 初期表示用のログ生成
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate() - 1);
        gameLogger.important("冒険者ギルドへようこそ！");
        gameLogger.info("「冒険者」メニューでは、ギルドに所属する冒険者の情報を確認できます。");
        gameLogger.info("「クエスト」メニューでは、クエスト受注状態を編集ができます。");
        gameLogger.info("右下の「NEXT」ボタンを押して、日付を進めてみましょう。");
        gameLogRepository.save(gameLogger);

        // 残高を保存
        GuildBalance.record(
                world.getId(),
                world.getGuild().getMoney().getValue(),
                world.getGameDate() - 1,
                accountingLogger);

        // 保存
        worldRepository.save(world);
        accountingRepository.save(accountingLogger);
    }
}
