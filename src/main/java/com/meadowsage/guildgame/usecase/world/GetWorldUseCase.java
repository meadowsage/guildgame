package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetWorldUseCase {
    private final WorldRepository worldRepository;

    public World run(String saveDataId) {
        return worldRepository.getWorld(saveDataId);
    }
}
