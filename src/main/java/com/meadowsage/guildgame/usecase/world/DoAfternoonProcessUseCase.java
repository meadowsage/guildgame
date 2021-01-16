package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoAfternoonProcessUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;

    public void run(long worldId) {
        GameWorld world = worldRepository.getGameWorld(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        if (!world.getState().equals(World.State.AFTERNOON)) return;

        world.afternoon(gameLogger);

        worldRepository.save(world);
        gameLogRepository.save(gameLogger);
    }
}
