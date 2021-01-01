package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.usecase.MarkAsReadScenarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/scenario")
public class ScenarioController {

    private final MarkAsReadScenarioUseCase markAsReadScenarioUseCase;

    @PostMapping("/{scenarioId}")
    @Transactional
    public void doneScenario(@PathVariable Long worldId, @PathVariable String scenarioId) {
        // TODO 入力チェック
        markAsReadScenarioUseCase.run(worldId, scenarioId);
    }
}