package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.accounting.Treasurer;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.TreasurerRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoNightProcessUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;
    private final TreasurerRepository treasurerRepository;

    public GameWorld run(long worldId) {
        GameWorld world = worldRepository.getGameWorld(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());
        Treasurer treasurer = Treasurer.builder().build();

        if (world.getState().equals(World.State.NIGHT)) {
            world.night(gameLogger, treasurer);
            worldRepository.save(world);
            gameLogRepository.save(gameLogger);
            treasurerRepository.save(treasurer);
        }

        return world;
    }
}
