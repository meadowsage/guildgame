package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.scenario.GetNextResponse;
import com.meadowsage.guildgame.usecase.scenario.GetNextScenarioUseCase;
import com.meadowsage.guildgame.usecase.scenario.MarkAsReadScenarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/scenario")
public class ScenarioController {

    private final GetNextScenarioUseCase getNextScenarioUseCase;
    private final MarkAsReadScenarioUseCase markAsReadScenarioUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetNextResponse getNext(@PathVariable String saveDataId) {
        return new GetNextResponse(getNextScenarioUseCase.run(saveDataId).getScenario());
    }

    @PostMapping("/{scenarioId}")
    @Transactional
    public void markAsRead(@PathVariable Long worldId, @PathVariable String scenarioId) {
        // TODO 入力チェック
        markAsReadScenarioUseCase.run(worldId, scenarioId);
    }
}