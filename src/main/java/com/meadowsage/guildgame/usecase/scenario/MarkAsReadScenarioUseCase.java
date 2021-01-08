package com.meadowsage.guildgame.usecase.scenario;

import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.scenario.ScenarioEnum;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.repository.ScenarioRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MarkAsReadScenarioUseCase {
    private final WorldRepository worldRepository;
    private final ScenarioRepository scenarioRepository;

    public void run(long worldId, String scenarioName) {
        GameWorld world = worldRepository.getGameWorld(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());
        Scenario scenario = ScenarioEnum.valueOf(scenarioName).getScenario();

        // 起動条件を満たさないシナリオの場合、エラー
        if (!scenario.canStart(world)) throw new IllegalArgumentException();

        // シナリオ完了後の処理
        scenario.afterProcess(world, gameLogger);

        scenarioRepository.markAsDone(worldId, scenarioName);
        worldRepository.save(world);
    }
}
