package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.mapper.ScenarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScenarioService {
    private final ScenarioMapper scenarioMapper;

    public void markAsDone(long worldId, String scenarioId) {
        scenarioMapper.insertCompletedRecord(worldId, scenarioId);
    }
}
