package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorldService {
    private final WorldRepository worldRepository;

    public World get(String saveDataId) {
        return worldRepository.get(saveDataId);
    }

    public void toNextDay(String savedataId) {
        World world = worldRepository.get(savedataId);
        world.toNextDay();
        worldRepository.update(world);
    }
}
