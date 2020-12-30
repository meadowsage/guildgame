package com.meadowsage.guildgame.model.scenario;

import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

public interface Scenario {
    List<Scenario> ALL_SCENARIOS = Arrays.asList(
            new Main0010(),
            new Main0020(),
            new Main0030()
    );

    int getId();

    String getTitle();

    boolean canStart(GameWorld world);

    List<ScenarioContent> getContents();

    @Data
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    class ScenarioContent {
        String speaker;
        String text;
        String image;
    }
}
