package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.system.GameLog;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetGameLogsResponse {
    List<ResponseGameLog> gameLogs;

    public GetGameLogsResponse(List<GameLog> gameLogs) {
        this.gameLogs = gameLogs.stream().map(gameLog -> {
            ResponseGameLog res = new ResponseGameLog();
            res.setMessage(gameLog.getMessage());
            res.setLogLevel(gameLog.getLogLevel());
            return res;
        }).collect(Collectors.toList());
    }

    @Data
    private static class ResponseGameLog {
        String message;
        int logLevel;
    }
}
