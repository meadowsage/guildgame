package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.StartNewGameResponse;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.usecase.game.DeleteSaveDataUseCase;
import com.meadowsage.guildgame.usecase.game.StartNewGameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * システム周りの操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {

    private final StartNewGameUseCase startNewGameUseCase;
    private final DeleteSaveDataUseCase deleteSaveDataUseCase;

    @PostMapping("")
    @ResponseBody
    @Transactional
    public StartNewGameResponse startNewGame() {
        SaveData saveData = startNewGameUseCase.run();
        return new StartNewGameResponse(saveData);
    }


    @DeleteMapping("/{saveDataId}")
    @Transactional
    public void deleteSaveData(@PathVariable String saveDataId) {
        deleteSaveDataUseCase.run(saveDataId);
    }
}