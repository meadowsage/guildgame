package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoMorningProcessUseCase {
    private final WorldRepository worldRepository;
    private final PersonRepository personRepository;

    public void run(long worldId) {
        World world = worldRepository.getWorld(worldId);

        if (world.getState().equals(World.State.MORNING)) {
            // キャラクターの行動済フラグをリセット
            List<Adventurer> adventurers = personRepository.getAdventurers(worldId);
            adventurers.forEach(Person::setAsNotActioned);

            world.changeState(World.State.MIDDAY);
            worldRepository.save(world);
            personRepository.save(adventurers, worldId);
        }
    }
}
