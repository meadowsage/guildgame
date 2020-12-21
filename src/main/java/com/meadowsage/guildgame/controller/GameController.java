package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.StartNewGameResponse;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.service.GameService;
import com.meadowsage.guildgame.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * システム周りの操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final WorldService worldService;

    @PostMapping("")
    @ResponseBody
    @Transactional(readOnly = true)
    public StartNewGameResponse startNewGame() {
        SaveData saveData = gameService.createNewSaveData();
        worldService.generateNewWorld(saveData);

        StartNewGameResponse response = new StartNewGameResponse();
        response.setSaveDataId(saveData.getId());
        return response;
    }
}