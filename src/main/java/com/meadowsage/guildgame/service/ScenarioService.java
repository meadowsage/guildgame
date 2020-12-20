package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.mapper.ScenarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {
    private final ScenarioMapper scenarioMapper;

    public void done(long worldId, int scenarioId) {
        scenarioMapper.insertCompletedRecord(worldId, scenarioId);
    }

    public List<Integer> getCompletedScenarioIds(long worldId){
        return scenarioMapper.select(worldId);
    }
}
