package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.system.GameLog;
import com.meadowsage.guildgame.usecase.ToNextUseCase;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ToNextResponse {
    GameWorld.State state;
    List<ResponseGameLog> gameLogs;
    String title;

    public ToNextResponse(ToNextUseCase.ToNextUseCaseResult result) {
        this.state = result.getWorld().getState();
        this.gameLogs = result.getGameLogs().stream().map(ResponseGameLog::new).collect(Collectors.toList());
        this.title = result.getTitle();
    }

    @Data
    private static class ResponseGameLog {
        String message;

        public ResponseGameLog(GameLog gameLog) {
            this.message = gameLog.getMessage();
        }
    }
}
