package com.meadowsage.guildgame.controller.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePartyNameRequest {
    private String partyName;
}
