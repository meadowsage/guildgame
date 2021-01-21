package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.controller.response.model.ResponseAdventurer;
import com.meadowsage.guildgame.model.person.Adventurer;
import lombok.Getter;

@Getter
public class GetRandomCommentResponse {
    ResponseAdventurer adventurer;
    String message;

    public GetRandomCommentResponse(Adventurer adventurer) {
        this.adventurer = new ResponseAdventurer(adventurer);
        this.message = adventurer.getRandomComment();
    }
}
