package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetWorldDataUseCase {
    private final WorldRepository worldRepository;

    public GameWorld run(String saveDataId) {
        return worldRepository.getGameWorld(saveDataId);
    }
}
