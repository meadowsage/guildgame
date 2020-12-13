package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.system.SaveData;
import lombok.Getter;

public class Game {
    @Getter
    private SaveData savedata;

    private World world;

    public static Game create() {
        Game game = new Game();
        game.savedata = SaveData.create();
        game.world = World.create();
        return game;
    }
}
