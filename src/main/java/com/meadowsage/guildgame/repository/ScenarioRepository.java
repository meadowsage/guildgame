package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.ScenarioMapper;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.scenario.ScenarioEnum;
import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScenarioRepository {

    private final ScenarioMapper scenarioMapper;

    public Optional<Scenario> getNextScenario(GameWorld world) {
        // 進行状況を取得
        List<String> completedScenarioIds = scenarioMapper.select(world.getId());
        // 未完了で開始条件を満たすイベントを抽出
        List<Scenario> scenarioContents = Arrays.stream(ScenarioEnum.values()).map(ScenarioEnum::getScenario).collect(Collectors.toList());
        return scenarioContents.stream()
                .filter(scenario -> !completedScenarioIds.contains(scenario.getId()))
                .filter(scenario -> scenario.canStart(world))
                .findFirst();
    }

    public void markAsDone(long worldId, String scenarioName) {
        scenarioMapper.insertCompletedRecord(worldId, scenarioName);
    }
}
