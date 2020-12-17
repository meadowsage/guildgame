package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLog;
import lombok.Data;

import java.util.List;

@Data
public class GetWorldResponse {
    World world;
    List<GameLog> gameLogs;
}
