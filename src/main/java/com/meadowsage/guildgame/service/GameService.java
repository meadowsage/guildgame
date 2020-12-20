package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ゲームシステム周りの処理
 * ワールドデータには変更を加えない
 */
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ScenarioService scenarioService;

    public SaveData createNewSaveData() {
        SaveData saveData = SaveData.create();
        gameRepository.createNewSaveData(saveData);
        return saveData;
    }

    public List<GameLog> getGameLogs(long worldId, int gameDate) {
        return gameRepository.getGameLogs(worldId, gameDate);
    }

    public List<Scenario> getScenarios(World world) {
        // 進行状況をDBから取得
        List<Integer> completedScenarioIds = scenarioService.getCompletedScenarioIds(world.getId());

        // 未完了で開始条件を満たすイベントを抽出
        List<Scenario> scenarios = Scenario.ALL_SCENARIOS;
        return scenarios.stream()
                .filter(scenario -> !completedScenarioIds.contains(scenario.getId()))
                .filter(scenario -> scenario.canStart(world))
                .collect(Collectors.toList());
    }
}
