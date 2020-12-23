package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.ScenarioRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetWorldDataUseCase {
    private final WorldRepository worldRepository;
    private final ScenarioRepository scenarioRepository;
    private final GameLogRepository gameLogRepository;

    public GetWorldDataUseCaseResult run(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        List<GameLog> gameLogs = gameLogRepository.getGameLogs(world.getId(), world.getGameDate() - 1);
        List<Scenario> scenarios = scenarioRepository.getNextScenarios(world);
        return new GetWorldDataUseCaseResult(world, gameLogs, scenarios);
    }

    @Data
    @AllArgsConstructor
    public static class GetWorldDataUseCaseResult {
        World world;
        List<GameLog> gameLogs;
        List<Scenario> scenarios;
    }
}
