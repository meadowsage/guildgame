package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoMidnightProcessUseCase {
    private final WorldRepository worldRepository;

    public GameWorld run(long worldId) {
        GameWorld world = worldRepository.getGameWorld(worldId);

        if (world.getState().equals(World.State.MIDNIGHT)) {
            world.midnight();
            worldRepository.save(world);
        }

        return world;
    }
}
