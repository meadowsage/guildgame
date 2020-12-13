package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public Game startNewGame() {
        // TODO ゲーム生成
        Game game = Game.create();
        // 保存

        return game;
    }
}
