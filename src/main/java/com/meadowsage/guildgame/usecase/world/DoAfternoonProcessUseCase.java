package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DoAfternoonProcessUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;

    public DoAfternoonProcessResult run(long worldId) {
        GameWorld world = worldRepository.getGameWorld(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        if (world.getState().equals(World.State.MIDDAY)) world.midday();

        if (!world.getState().equals(World.State.AFTERNOON)) {
            return new DoAfternoonProcessResult(world, Optional.empty());
        }

        Optional<Quest> quest = world.afternoon(gameLogger);

        worldRepository.save(world);
        gameLogRepository.save(gameLogger);

        return new DoAfternoonProcessResult(world, quest);
    }

    @AllArgsConstructor
    @Getter
    public static class DoAfternoonProcessResult {
        GameWorld world;
        Optional<Quest> quest;
    }
}
