package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestProcess;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToNextUseCase {
    private final WorldRepository worldRepository;
    private final GameLogRepository gameLogRepository;

    public ToNextUseCaseResult run(String saveDataId) {
        World world = worldRepository.get(saveDataId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());

        switch (world.getState()) {
            case MORNING:
                // 朝→昼
                world.setState(World.State.DAYTIME);
            case DAYTIME:
                // 未実行のクエストがあれば実行
                // FIXME world.daytime() に切り出す！！
                Optional<Quest> nextQuest = world.getNextQuest(world.getGameDate());
                if (nextQuest.isPresent()) {
                    List<Person> party = nextQuest.get().getQuestOrders().stream()
                            .map(questOrder -> world.findPerson(questOrder.getPersonId()).orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    QuestProcess questProcess = new QuestProcess(nextQuest.get(), party, world.getGameDate());
                    questProcess.run(new Dice(), gameLogger);
                } else {
                    // クエスト処理が完了していれば、未行動のキャラクターの行動
                    world.getAdventurers().stream()
                            .filter(person -> person instanceof Adventurer) // 念のためフィルタ
                            .map(person -> (Adventurer) person)
                            .filter(adventurer -> !adventurer.isActioned())
                            .forEach(person -> person.doDaytimeActivity(world, gameLogger));
                    // 夜の処理
                    world.setState(World.State.NIGHT);
                    world.night(gameLogger);
                    // カミサマ時間（Worldリソース生成）
                    // IDが付与されていないリソースができるので注意！
                    world.godTime();
                }
                worldRepository.save(world);
                gameLogRepository.saveGameLog(gameLogger);
                return new ToNextUseCaseResult(world, nextQuest.orElse(null), gameLogger.getLogs());
            case NIGHT:
                // 朝の処理
                world.morning();
                world.setState(World.State.MORNING);
                worldRepository.save(world);
                return new ToNextUseCaseResult(world, null, gameLogger.getLogs());
            default:
                throw new IllegalStateException();
        }
    }

    @Data
    public static class ToNextUseCaseResult {
        World world;
        @Nullable
        Quest quest;
        List<GameLog> gameLogs;

        public ToNextUseCaseResult(World world, Quest quest, List<GameLog> gameLogs) {
            this.world = world;
            this.quest = quest;
            this.gameLogs = gameLogs;
        }
    }
}
