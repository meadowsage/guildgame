package com.meadowsage.guildgame.controller.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePartyRequest {
    private String name;
    private List<Long> memberIds;
}
