package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.repository.ScenarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MarkAsReadScenarioUseCase {
    private final ScenarioRepository scenarioRepository;

    public void run(long worldId, int scenarioId) {
        scenarioRepository.markAsDone(worldId, scenarioId);
    }
}
