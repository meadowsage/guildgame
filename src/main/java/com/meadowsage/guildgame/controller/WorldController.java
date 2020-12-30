package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.world.DoAfternoonProcessResponse;
import com.meadowsage.guildgame.controller.response.world.GetWorldResponse;
import com.meadowsage.guildgame.usecase.GetWorldDataUseCase;
import com.meadowsage.guildgame.usecase.world.DoAfternoonProcessUseCase;
import com.meadowsage.guildgame.usecase.world.DoMidnightProcessUseCase;
import com.meadowsage.guildgame.usecase.world.DoMorningProcessUseCase;
import com.meadowsage.guildgame.usecase.world.DoNightProcessUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ワールドデータに対する処理
 */
@RestController
@RequiredArgsConstructor
// FIXME /api/world
@RequestMapping("/api/{saveDataId}/world")
@Transactional
public class WorldController {

    private final DoMorningProcessUseCase doMorningProcessUseCase;
    private final DoAfternoonProcessUseCase doAfternoonProcessUseCase;
    private final DoNightProcessUseCase doNightProcessUseCase;
    private final DoMidnightProcessUseCase doMidnightProcessUseCase;
    private final GetWorldDataUseCase getWorldDataUseCase;

    // FIXME セーブデータIDはクエリパラメータで受け取る
    @GetMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetWorldResponse getWorld(@PathVariable String saveDataId) {
        GetWorldDataUseCase.GetWorldDataUseCaseResult result = getWorldDataUseCase.run(saveDataId);
        return new GetWorldResponse(result.getWorld(), result.getGameLogs(), result.getScenarios());
    }

    @PutMapping("/{worldId}/morning")
    @Transactional
    public void doMorningProcess(@PathVariable long worldId) {
        doMorningProcessUseCase.run(worldId);
    }

    @PutMapping("/{worldId}/afternoon")
    @ResponseBody
    @Transactional
    public DoAfternoonProcessResponse doAfternoonProcess(@PathVariable long worldId) {
        return new DoAfternoonProcessResponse(doAfternoonProcessUseCase.run(worldId));
    }

    @PutMapping("/{worldId}/night")
    @Transactional
    public void doNightProcess(@PathVariable long worldId) {
        doNightProcessUseCase.run(worldId);
    }

    @PutMapping("/{worldId}/midnight")
    @Transactional
    public void doMidnightProcess(@PathVariable long worldId) {
        doMidnightProcessUseCase.run(worldId);
    }
}