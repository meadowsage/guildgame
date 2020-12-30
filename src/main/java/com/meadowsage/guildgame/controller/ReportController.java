package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetDailyReportResponse;
import com.meadowsage.guildgame.usecase.GetDailyReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ワールドデータに対する処理
 */
@RestController
@RequiredArgsConstructor
// FIXME /api/world
@RequestMapping("/api/{saveDataId}/world/{worldId}")
public class ReportController {

    private final GetDailyReportUseCase getDailyReportUseCase;

    @GetMapping("/daily-report")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetDailyReportResponse getDailyReport(@PathVariable long worldId, @RequestParam int gameDate) {
        GetDailyReportUseCase.GetDailyReportUseCaseResult result = getDailyReportUseCase.run(worldId, gameDate);
        return new GetDailyReportResponse(result);
    }
}