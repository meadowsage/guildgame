package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.usecase.ToNextUseCase;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ToNextResponse {
    World.State state;
    Long questId;
    List<ResponseGameLog> gameLogs;

    public ToNextResponse(ToNextUseCase.ToNextUseCaseResult result) {
        this.state = result.getWorld().getState();
        if (result.getQuest() != null) this.questId = result.getQuest().getId();
        this.gameLogs = result.getGameLogs().stream().map(ResponseGameLog::new).collect(Collectors.toList());
    }

    @Data
    private static class ResponseGameLog {
        String message;

        public ResponseGameLog(GameLog gameLog) {
            this.message = gameLog.getMessage();
        }
    }
}
