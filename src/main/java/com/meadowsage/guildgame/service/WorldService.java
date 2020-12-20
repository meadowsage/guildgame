package com.meadowsage.guildgame.service;

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

    public World get(String saveDataId) {
        return worldRepository.get(saveDataId);
    }

    public void generateNewWorld(SaveData saveData) {
        // 初期化処理
        World world = World.create();
        // 作成
        worldRepository.create(world, saveData.getId());
        // 初期表示用のログ生成
        GameLogger logger = new GameLogger(world.getId(), 0);
        logger.add("冒険者ギルドへようこそ！");
        logger.add("「冒険者」メニューでは、所属する冒険者の情報を確認できます");
        logger.add("「クエスト」メニューでは、クエスト受注状態の確認や編集ができます");
        logger.add("確認が終わったら、右上のボタンで日付を進めてみましょう");
        gameRepository.saveGameLog(logger);
        // 初日の処理 TODO 受注サービスを作ったら単体で受注させる
        world.morning();
        // 更新
        worldRepository.save(world);
    }

    public void toNextDay(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        GameLogger logger = new GameLogger(world.getId(), world.getGameDate());

        world.daytime(logger);

        world.night();
        worldRepository.saveNewDataAndSetIds(world);

        world.morning();

        gameRepository.saveGameLog(logger);
        worldRepository.save(world);
    }
}
