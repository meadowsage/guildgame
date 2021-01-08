package com.meadowsage.guildgame.usecase.scenario;

import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.repository.ScenarioRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetNextScenarioUseCase {
    private final WorldRepository worldRepository;
    private final ScenarioRepository scenarioRepository;

    public GetNextScenarioUseCaseResult run(String saveDataId) {
        GameWorld world = worldRepository.getGameWorld(saveDataId);
        Optional<Scenario> nextScenario = scenarioRepository.getNextScenario(world);
        return new GetNextScenarioUseCaseResult(nextScenario.orElse(null));
    }

    @Data
    @AllArgsConstructor
    public static class GetNextScenarioUseCaseResult {
        @Nullable Scenario scenario;
    }
}
