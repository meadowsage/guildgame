package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.quest.QuestProcess;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoAfternoonProcessUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;
    private final QuestRepository questRepository;
    private final PartyRepository partyRepository;
    private final PersonRepository personRepository;

    public void run(long worldId) {
        World world = worldRepository.getWorld(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        if (!world.getState().equals(World.State.AFTERNOON)) return;

        // 未処理のクエスト受注を取得
        Optional<QuestOrder> nextQuestOrder = questRepository.getNextQuestOrder(worldId, world.getGameDate());

        if (nextQuestOrder.isPresent()) {
            Quest quest = questRepository.get(nextQuestOrder.get().getQuestId())
                    .orElseThrow(IllegalStateException::new);
            Party party = partyRepository.get(nextQuestOrder.get().getPartyId())
                    .orElseThrow(IllegalStateException::new);

            new QuestProcess(quest, nextQuestOrder.get(), party, world).run(new Dice(), gameLogger);

            questRepository.saveQuestOrder(nextQuestOrder.get());
            partyRepository.save(party, worldId);
        } else {
            // クエスト処理が全て完了していれば、未行動のキャラクターの行動
            List<Adventurer> adventurers = personRepository.getAdventurers(worldId);
            adventurers.stream()
                    .filter(adventurer -> !adventurer.isActioned())
                    .forEach(person -> person.doDaytimeActivity(gameLogger));

            personRepository.save(adventurers, worldId);
            world.changeState(World.State.NIGHT);
        }

        worldRepository.save(world);
        gameLogRepository.save(gameLogger);
    }
}
