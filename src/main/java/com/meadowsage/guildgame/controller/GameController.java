package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.model.Game;
import com.meadowsage.guildgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("/new")
    public String startNewGame() {
        Game newGame = gameService.startNewGame();
        return newGame.getSavedata().getId();
    }
}