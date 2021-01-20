package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GameLogRepository;
import com.meadowsage.guildgame.repository.PartyRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAfternoonProcessResultUseCase {
    private final WorldRepository worldRepository;
    private final QuestRepository questRepository;
    private final PartyRepository partyRepository;
    private final GameLogRepository gameLogRepository;

    public GetAfternoonProcessResultUseCaseResult run(long worldId) {
        World world = worldRepository.getWorld(worldId);
        int gameDate = world.getGameDate();

        // 現在のWorldStateがAFTERNOONでない場合、すべて処理済と判断して「その他行動」のログを返す
        if (!world.getState().equals(World.State.AFTERNOON)) {
            List<GameLog> gameLogs = gameLogRepository.getOtherActionGameLogs(worldId, gameDate);
            return GetAfternoonProcessResultUseCaseResult.otherActions(gameLogs);
        }

        // 最後に実行されたクエスト受注を取得
        Optional<QuestOrder> lastProcessedQuestOrder = questRepository.getLastProcessedQuestOrder(worldId, gameDate);

        // まだクエストが１件も実行されていない場合、空のレスポンスを返す
        if (!lastProcessedQuestOrder.isPresent()) return GetAfternoonProcessResultUseCaseResult.empty();

        // クエスト情報、パーティ情報、ログを取得
        Quest lastProcessedQuest = questRepository.get(lastProcessedQuestOrder.get().getQuestId())
                .orElseThrow(IllegalStateException::new);
        Party party = partyRepository.get(lastProcessedQuestOrder.get().getPartyId())
                .orElseThrow(IllegalStateException::new);
        List<GameLog> gameLogs = gameLogRepository.getQuestGameLogs(worldId, gameDate, lastProcessedQuest.getId());

        return new GetAfternoonProcessResultUseCaseResult(lastProcessedQuest, party, gameLogs, false);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAfternoonProcessResultUseCaseResult {
        @Nullable
        Quest lastProcessedQuest;
        @Nullable
        Party party;
        List<GameLog> gameLogs = new ArrayList<>();
        boolean isDone = false;

        public static GetAfternoonProcessResultUseCaseResult empty() {
            return new GetAfternoonProcessResultUseCaseResult(
                    null, null, new ArrayList<>(), false);
        }

        public static GetAfternoonProcessResultUseCaseResult otherActions(List<GameLog> otherActionGameLogs) {
            return new GetAfternoonProcessResultUseCaseResult(
                    null, null, otherActionGameLogs, true);
        }
    }
}
