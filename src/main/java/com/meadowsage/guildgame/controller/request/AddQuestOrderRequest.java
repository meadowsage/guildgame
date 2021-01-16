package com.meadowsage.guildgame.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddQuestOrderRequest {
    private long questId;
    private long partyId;
}
