package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.mapper.GameLogMapper;
import com.meadowsage.guildgame.model.system.GameLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 時間を進める処理
 */
@Service
@AllArgsConstructor
public class GetGameLogsUseCase {
    private final GameLogMapper gameLogMapper;

    public List<GameLog> run(long worldId, Long questId) {
        return gameLogMapper.selectByQuestId(worldId, questId);
    }
}
