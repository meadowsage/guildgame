package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world")
public class WorldController {

    private final WorldService worldService;

    @GetMapping("")
    @ResponseBody
    public World getWorld(@PathVariable String saveDataId) {
        return worldService.get(saveDataId);
    }

    @PutMapping("")
    public void toNextDay(@PathVariable String saveDataId) {
        worldService.toNextDay(saveDataId);
    }
}