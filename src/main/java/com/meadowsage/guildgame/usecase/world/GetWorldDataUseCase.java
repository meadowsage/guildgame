package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.ScenarioRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetWorldDataUseCase {
    private final WorldRepository worldRepository;
    private final ScenarioRepository scenarioRepository;
    private final GameLogRepository gameLogRepository;

    public GetWorldDataUseCaseResult run(String saveDataId) {
        GameWorld world = worldRepository.getGameWorld(saveDataId);
        List<GameLog> gameLogs = gameLogRepository.getGameLogs(world.getId(), world.getGameDate() - 1);
        Optional<Scenario> nextScenario = scenarioRepository.getNextScenario(world);
        return new GetWorldDataUseCaseResult(world, gameLogs, nextScenario.orElse(null));
    }

    @Data
    @AllArgsConstructor
    public static class GetWorldDataUseCaseResult {
        GameWorld world;
        List<GameLog> gameLogs;
        Scenario scenario;
    }
}
