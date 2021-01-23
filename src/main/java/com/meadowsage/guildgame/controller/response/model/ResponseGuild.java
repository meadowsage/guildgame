package com.meadowsage.guildgame.controller.response.model;

import com.meadowsage.guildgame.model.Guild;
import lombok.Getter;

@Getter
public class ResponseGuild {
    long money;
    int reputation;
    int limitOfAdventurers;

    public ResponseGuild(Guild guild) {
        this.money = guild.getMoney().getValue();
        this.reputation = guild.getReputation();
        this.limitOfAdventurers = guild.getLimitOfAdventurers();
    }
}
