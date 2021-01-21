package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRandomCommentUseCase {
    private final PersonRepository personRepository;

    public GetRandomCommentUseCaseResult run(long worldId) {
        List<Adventurer> adventurers = personRepository.getAdventurers(worldId);
        Dice dice = new Dice();
        int result = dice.roll(1, adventurers.size());
//        if (result > adventurers.size()) {
//            return new GetRandomCommentUseCaseResult("エリス", "おはようございます。\n……何か、ご用でも？", "face_1.png");
//        } else {
            Adventurer adventurer = adventurers.get(result - 1);
            return new GetRandomCommentUseCaseResult(adventurer);
//        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetRandomCommentUseCaseResult {
        Adventurer adventurer;
    }
}
