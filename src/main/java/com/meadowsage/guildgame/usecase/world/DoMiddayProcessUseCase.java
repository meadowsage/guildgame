package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoMiddayProcessUseCase {
    private final WorldRepository worldRepository;

    public void run(long worldId) {
        World world = worldRepository.getGameWorld(worldId);
        if (!world.getState().equals(World.State.MIDDAY)) return;

        world.changeState(World.State.AFTERNOON);
        worldRepository.save(world);
    }
}
