package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.mapper.PersonMapper;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorldService {
    private final GameRepository gameRepository;
    private final WorldRepository worldRepository;
    private final PersonMapper personMapper;

    public World get(String saveDataId) {
        return worldRepository.get(saveDataId);
    }

    public void generateNewWorld(SaveData saveData) {
        // 初期化処理
        World world = World.create();
        // 作成
        worldRepository.create(world, saveData.getId());
        // 初期表示用のログ生成
        GameLogger gameLogger = new GameLogger(world.getId(), 0);
        gameLogger.add("冒険者ギルドへようこそ！");
        gameLogger.add("「冒険者」メニューでは、所属する冒険者の情報を確認できます");
        gameLogger.add("「クエスト」メニューでは、クエスト受注状態の確認や編集ができます");
        gameLogger.add("確認が終わったら、右上のボタンで日付を進めてみましょう");
        gameRepository.saveGameLog(gameLogger);
        // 初日の処理 TODO 受注サービスを作ったら単体で受注させる
        world.morning(gameLogger);
        // 更新
        worldRepository.save(world);
    }

    public void toNextDay(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        world.daytime(gameLogger);
        world.night(gameLogger);

        // 古いリソースを削除
        world.deleteOldResources(personMapper);

        // 新しいリソースを作成してIDを払い出す
        world.generateNewResources(worldRepository);

        world.morning(gameLogger);

        gameRepository.saveGameLog(gameLogger);
        worldRepository.save(world);
    }
}
