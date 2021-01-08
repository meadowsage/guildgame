package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAfternoonProcessResultUseCase {
    private final WorldRepository worldRepository;
    private final QuestRepository questRepository;
    private final PersonRepository personRepository;
    private final GameLogRepository gameLogRepository;

    public GetAfternoonProcessResultUseCaseResult run(long worldId) {
        World world = worldRepository.getWorld(worldId);
        int gameDate = world.getGameDate();

        // 現在のWorldStateがAFTERNOONでない場合、すべて処理済と判断して「その他行動」のログを返す
        if (!world.getState().equals(World.State.AFTERNOON)) {
            List<GameLog> gameLogs = gameLogRepository.getGameLogsWithQuestIdNull(worldId, gameDate);
            return new GetAfternoonProcessResultUseCaseResult(null, new ArrayList<>(), gameLogs, true);
        }

        List<Quest> quests = questRepository.getQuests(worldId);
        List<Quest> processedQuests = quests.stream()
                .filter(quest -> quest.hasProcessed(gameDate))
                .collect(Collectors.toList());

        // まだクエストが１件も実行されていない場合、空のレスポンスを返す
        if (processedQuests.size() == 0) return new GetAfternoonProcessResultUseCaseResult();

        // 実行済みのクエストがある場合、クエスト詳細、パーティ情報、ログを返す
        Quest lastProcessedQuest = processedQuests.stream()
                .max(Comparator.comparing(Quest::getId))
                .orElseThrow(IllegalStateException::new);
        List<Adventurer> party = personRepository.getAdventurers(
                worldId,
                lastProcessedQuest.getQuestOrders().stream()
                        .map(QuestOrder::getPersonId)
                        .collect(Collectors.toList()));
        List<GameLog> gameLogs = gameLogRepository.getQuestGameLogs(worldId, gameDate, lastProcessedQuest.getId());
        return new GetAfternoonProcessResultUseCaseResult(lastProcessedQuest, party, gameLogs, false);
    }

    @Getter
    @AllArgsConstructor
    public static class GetAfternoonProcessResultUseCaseResult {
        @Nullable
        Quest lastProcessedQuest;
        List<Adventurer> party;
        List<GameLog> gameLogs;
        boolean isDone;

        public GetAfternoonProcessResultUseCaseResult() {
            this.party = new ArrayList<>();
            this.gameLogs = new ArrayList<>();
            this.isDone = false;
        }
    }
}
