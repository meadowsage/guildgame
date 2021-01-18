package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.StartNewGameResponse;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.usecase.game.DeleteSaveDataUseCase;
import com.meadowsage.guildgame.usecase.game.StartNewGameUseCase;
import com.meadowsage.guildgame.usecase.party.GetRandomPartyNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
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