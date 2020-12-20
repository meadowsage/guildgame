package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.SaveData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameServiceTests {
    @Autowired
    private GameService gameService;
    @Autowired
    private WorldService worldService;

    @Test
    public void test() {
        SaveData saveData = gameService.createNewSaveData();
        World world = worldService.get(saveData.getId());

        System.out.println(saveData);
        System.out.println(world);
    }
}
