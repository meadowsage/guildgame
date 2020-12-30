package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetGameLogsResponse;
import com.meadowsage.guildgame.usecase.GetQuestGameLogsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/logs")
public class GameLogController {

    private final GetQuestGameLogsUseCase getGameLogsUseCase;

    @GetMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetGameLogsResponse getQuestGameLogs(
            @PathVariable Long worldId,
            @RequestParam Integer gameDate,
            @RequestParam Boolean otherActions,
            @RequestParam(required = false) Long questId
    ) {
        return new GetGameLogsResponse(getGameLogsUseCase.run(worldId, gameDate, questId, otherActions));
    }
}