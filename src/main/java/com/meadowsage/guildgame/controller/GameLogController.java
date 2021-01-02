package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetGameLogsResponse;
import com.meadowsage.guildgame.usecase.GetGameLogsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/logs")
public class GameLogController {

    private final GetGameLogsUseCase getGameLogsUseCase;

    @GetMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetGameLogsResponse getGameLogs(
            @PathVariable Long worldId,
            @RequestParam Integer gameDate,
            @RequestParam(required = false) Boolean noQuestId,
            @RequestParam(required = false) Boolean noPersonId,
            @RequestParam(required = false) Long questId
    ) {
        return new GetGameLogsResponse(getGameLogsUseCase.run(worldId, gameDate, questId, noQuestId, noPersonId));
    }
}