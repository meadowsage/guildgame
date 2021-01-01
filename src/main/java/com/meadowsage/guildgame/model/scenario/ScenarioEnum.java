package com.meadowsage.guildgame.model.scenario;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ScenarioEnum {
    MAIN_0010(new Main0010("MAIN_0010")),
    MAIN_0020(new Main0020("MAIN_0020")),
    MAIN_0030(new Main0030("MAIN_0030"));

    private final Scenario scenario;
}
