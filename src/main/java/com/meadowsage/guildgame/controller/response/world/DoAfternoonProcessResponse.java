package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.usecase.world.DoAfternoonProcessUseCase;
import lombok.Data;

@Data
public class DoAfternoonProcessResponse {
    GameWorld.State state;
    ResponseQuest quest;

    public DoAfternoonProcessResponse(DoAfternoonProcessUseCase.DoAfternoonProcessResult result) {
        this.state = result.getWorld().getState();
        this.quest = result.getQuest().isPresent() ? new ResponseQuest(result.getQuest().get()) : null;
    }

    @Data
    private static class ResponseQuest {
        long id;
        String name;

        public ResponseQuest(Quest quest) {
            this.id = quest.getId();
            this.name = quest.getName();
        }
    }
}
