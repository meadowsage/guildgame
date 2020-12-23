package com.meadowsage.guildgame.controller.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddQuestOrderRequest {
    private long questId;
    private long personId;
}
