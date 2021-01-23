package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.controller.response.model.ResponseGuild;
import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.world.World;
import lombok.Getter;

@Getter
public class GetWorldResponse {
    ResponseWorld world;
    ResponseGuild guild;

    public GetWorldResponse(World world, Guild guild) {
        this.world = new ResponseWorld(world);

        this.guild = new ResponseGuild(guild);
    }

    @Getter
    private static class ResponseWorld {
        long id;
        int gameDate;
        String state;

        public ResponseWorld (World world) {
            this.id = world.getId();
            this.gameDate = world.getGameDate();
            this.state = world.getState().name();
        }
    }
}
