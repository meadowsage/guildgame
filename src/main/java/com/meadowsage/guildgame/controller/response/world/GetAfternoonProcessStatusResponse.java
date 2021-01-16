package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.controller.response.model.ResponseParty;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLog;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAfternoonProcessStatusResponse {
    ResponseQuest quest;
    ResponseParty party;
    List<ResponseGameLog> gameLogs;
    boolean isDone;

    public GetAfternoonProcessStatusResponse(
            @Nullable Quest quest,
            @Nullable Party party,
            List<GameLog> gameLogs,
            boolean isDone
    ) {
        if (quest != null) this.quest = new ResponseQuest(quest);
        if (party != null) this.party = new ResponseParty(party);
        this.gameLogs = gameLogs.stream().map(ResponseGameLog::new).collect(Collectors.toList());
        this.isDone = isDone;
    }

    @Getter
    private static class ResponseQuest {
        long id;
        String name;

        public ResponseQuest(Quest quest) {
            this.id = quest.getId();
            this.name = quest.getName();
        }
    }

    @Getter
    private static class ResponseGameLog {
        String message;
        int logLevel;

        public ResponseGameLog(GameLog gameLog) {
            this.message = gameLog.getMessage();
            this.logLevel = gameLog.getLogLevel();
        }
    }
}
