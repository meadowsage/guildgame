package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetGameLogsResponse;
import com.meadowsage.guildgame.controller.response.GetWorldResponse;
import com.meadowsage.guildgame.controller.response.ToNextResponse;
import com.meadowsage.guildgame.usecase.GetGameLogsUseCase;
import com.meadowsage.guildgame.usecase.GetWorldDataUseCase;
import com.meadowsage.guildgame.usecase.ToNextUseCase;
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

    private final ToNextUseCase toNextUseCase;
    private final GetGameLogsUseCase getGameLogsUseCase;
    private final GetWorldDataUseCase getWorldDataUseCase;

    // FIXME セーブデータIDはクエリパラメータで受け取る
    @GetMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetWorldResponse getWorld(@PathVariable String saveDataId) {
        GetWorldDataUseCase.GetWorldDataUseCaseResult result = getWorldDataUseCase.run(saveDataId);
        return new GetWorldResponse(result.getWorld(), result.getGameLogs(), result.getScenarios());
    }

    // FIXME セーブデータIDはフォームで受け取る
    @PutMapping("")
    @ResponseBody
    @Transactional
    public ToNextResponse toNextDay(@PathVariable String saveDataId) {
        // TODO ゲーム日付のチェック
        return new ToNextResponse(toNextUseCase.run(saveDataId));
    }

    @GetMapping("/{worldId}/logs")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetGameLogsResponse getGameLogs(
            @PathVariable String saveDataId,
            @PathVariable Long worldId,
            @RequestParam Long questId
    ) {
        System.out.println(saveDataId);
        return new GetGameLogsResponse(getGameLogsUseCase.run(worldId, questId));
    }
}