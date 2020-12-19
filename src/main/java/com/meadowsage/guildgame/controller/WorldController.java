package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetWorldResponse;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.service.GameService;
import com.meadowsage.guildgame.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world")
public class WorldController {

    private final GameService gameService;
    private final WorldService worldService;

    @GetMapping("")
    @ResponseBody
    public GetWorldResponse getWorld(@PathVariable String saveDataId) {
        World world = worldService.get(saveDataId);
        List<GameLog> gameLogs = gameService.getGameLogs(world.getId(), world.getGameDate() - 1);

        return new GetWorldResponse(world, gameLogs);
    }

    @PutMapping("")
    public void toNextDay(@PathVariable String saveDataId) {
        worldService.toNextDay(saveDataId);
    }
}