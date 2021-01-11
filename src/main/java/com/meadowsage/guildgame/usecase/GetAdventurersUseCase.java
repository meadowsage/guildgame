package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAdventurersUseCase {
    private final PersonRepository personRepository;

    public GetAdventurersUseCaseResult run(long worldId) {
        List<Adventurer> adventurers = personRepository.getAdventurers(worldId);
        return new GetAdventurersUseCaseResult(adventurers);
    }

    @Data
    @AllArgsConstructor
    public static class GetAdventurersUseCaseResult {
        List<Adventurer> adventurers;
    }
}
