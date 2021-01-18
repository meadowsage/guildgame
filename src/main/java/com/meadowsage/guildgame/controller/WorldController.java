package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.world.GetAfternoonProcessStatusResponse;
import com.meadowsage.guildgame.controller.response.world.GetRandomCommentResponse;
import com.meadowsage.guildgame.controller.response.world.GetWorldResponse;
import com.meadowsage.guildgame.usecase.world.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ワールドデータに対する処理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds")
@Transactional
public class WorldController {

    private final GetWorldDataUseCase getWorldDataUseCase;
    private final DoMorningProcessUseCase doMorningProcessUseCase;
    private final DoMiddayProcessUseCase doMiddayProcessUseCase;
    private final DoAfternoonProcessUseCase doAfternoonProcessUseCase;
    private final GetAfternoonProcessResultUseCase getAfternoonProcessResultUseCase;
    private final DoNightProcessUseCase doNightProcessUseCase;
    private final DoMidnightProcessUseCase doMidnightProcessUseCase;
    private final GetRandomCommentUseCase getRandomCommentUseCase;

    // FIXME セーブデータIDはクエリパラメータで受け取る
    @GetMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetWorldResponse getWorld(@PathVariable String saveDataId) {
        GetWorldDataUseCase.GetWorldDateUseCaseResult result = getWorldDataUseCase.run(saveDataId);
        return new GetWorldResponse(result.getWorld(), result.getGuild());
    }

    @PutMapping("/{worldId}/morning")
    @Transactional
    public void doMorningProcess(@PathVariable long worldId) {
        doMorningProcessUseCase.run(worldId);
    }

    @PutMapping("/{worldId}/midday")
    @Transactional
    public void doMiddayProcess(@PathVariable long worldId) {
        doMiddayProcessUseCase.run(worldId);
    }

    @GetMapping("/{worldId}/afternoon")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetAfternoonProcessStatusResponse getAfternoonProcessStatus(@PathVariable long worldId) {
        GetAfternoonProcessResultUseCase.GetAfternoonProcessResultUseCaseResult result =
                getAfternoonProcessResultUseCase.run(worldId);

        return new GetAfternoonProcessStatusResponse(
                result.getLastProcessedQuest(),
                result.getParty(),
                result.getGameLogs(),
                result.isDone());
    }

    @PutMapping("/{worldId}/afternoon")
    @Transactional
    public void doAfternoonProcess(@PathVariable long worldId) {
        doAfternoonProcessUseCase.run(worldId);
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

    @GetMapping("/{worldId}/comment")
    @Transactional(readOnly = true)
    public GetRandomCommentResponse getRandomComment(@PathVariable long worldId) {
        return new GetRandomCommentResponse(getRandomCommentUseCase.run(worldId));
    }
}