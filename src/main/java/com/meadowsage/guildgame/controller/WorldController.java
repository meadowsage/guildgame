package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetWorldResponse;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.service.GameService;
import com.meadowsage.guildgame.service.ScenarioService;
import com.meadowsage.guildgame.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ワールドデータに対する処理
 */
@RestController
@RequiredArgsConstructor
// FIXME /api/world
@RequestMapping("/api/{saveDataId}/world")
public class WorldController {

    private final GameService gameService;
    private final WorldService worldService;
    private final ScenarioService scenarioService;

    // FIXME セーブデータIDはクエリパラメータで受け取る
    @GetMapping("")
    @ResponseBody
    public GetWorldResponse getWorld(@PathVariable String saveDataId) {
        World world = worldService.get(saveDataId);
        List<GameLog> gameLogs = gameService.getGameLogs(world.getId(), world.getGameDate() - 1);
        List<Scenario> scenarios = gameService.getScenarios(world);

        return new GetWorldResponse(world, gameLogs, scenarios);
    }

    // FIXME セーブデータIDはフォームで受け取る
    @PutMapping("")
    public void toNextDay(@PathVariable String saveDataId) {
        worldService.toNextDay(saveDataId);
    }

    @PostMapping("/{worldId}/scenario/{scenarioId}")
    @ResponseBody
    public void doneScenario(
            @PathVariable Long worldId,
            @PathVariable Integer scenarioId
    ) {
        // TODO 入力チェック
        scenarioService.done(worldId, scenarioId);
    }
}