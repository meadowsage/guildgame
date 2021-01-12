package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetWorldResponse {
    ResponseWorld world;
    ResponseGuild guild;

    public GetWorldResponse(GameWorld world) {
        this.world = ResponseWorld.builder()
                .id(world.getId())
                .gameDate(world.getGameDate())
                .state(world.getState().name()).build();

        this.guild = ResponseGuild.builder()
                .money(world.getGuild().getMoney().getValue())
                .reputation(world.getGuild().getReputation()).build();
    }

    @Builder
    @Getter
    private static class ResponseWorld {
        long id;
        int gameDate;
        String state;
    }

    @Builder
    @Getter
    private static class ResponseGuild {
        long money;
        int reputation;
    }
}
