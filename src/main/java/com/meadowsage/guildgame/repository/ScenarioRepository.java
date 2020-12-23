package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.ScenarioMapper;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.scenario.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScenarioRepository {

    private final ScenarioMapper scenarioMapper;

    public List<Scenario> getNextScenarios(World world) {
        // 進行状況を取得
        List<Integer> completedScenarioIds = scenarioMapper.select(world.getId());
        // 未完了で開始条件を満たすイベントを抽出
        List<Scenario> scenarios = Scenario.ALL_SCENARIOS;
        return scenarios.stream()
                .filter(scenario -> !completedScenarioIds.contains(scenario.getId()))
                .filter(scenario -> scenario.canStart(world))
                .collect(Collectors.toList());
    }

    public void markAsDone(long worldId, int scenarioId) {
        scenarioMapper.insertCompletedRecord(worldId, scenarioId);
    }
}
