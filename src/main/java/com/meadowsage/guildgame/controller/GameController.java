package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.StartNewGameResponse;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.usecase.StartNewGameUseCase;
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

    private final StartNewGameUseCase startNewGameUseCase;

    @PostMapping("")
    @ResponseBody
    @Transactional
    public StartNewGameResponse startNewGame() {
        SaveData saveData = startNewGameUseCase.run();
        return new StartNewGameResponse(saveData);
    }
}