package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.model.person.Adventurer;
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
    List<ResponseAdventurer> party;
    List<ResponseGameLog> gameLogs;
    boolean isDone;

    public GetAfternoonProcessStatusResponse(
            @Nullable Quest quest,
            List<Adventurer> party,
            List<GameLog> gameLogs,
            boolean isDone
    ) {
        if (quest != null) this.quest = new ResponseQuest(quest);
        this.party = party.stream().map(ResponseAdventurer::new).collect(Collectors.toList());
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
    private static class ResponseAdventurer {
        long id;
        String name;
        String fullName;
        int battle;
        int knowledge;
        int support;
        int energy;
        int maxEnergy;
        String imageBodyFileName;

        public ResponseAdventurer(Adventurer adventurer) {
            this.id = adventurer.getId();
            this.name = adventurer.getName().getFirstName();
            this.fullName = adventurer.getName().getFullName();
            this.battle = adventurer.getBattle().getValue();
            this.knowledge = adventurer.getKnowledge().getValue();
            this.support = adventurer.getSupport().getValue();
            this.energy = adventurer.getEnergy().getValue();
            this.maxEnergy = adventurer.getEnergy().getMax();
            this.imageBodyFileName = adventurer.getImageBodyFileName();
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
