package com.meadowsage.guildgame.model.scenario;

import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public abstract class Scenario {
    @Getter
    private final String id;

    public abstract String getTitle();

    public abstract List<ScenarioScript> getScripts();

    public abstract boolean canStart(GameWorld world);

    public abstract void afterProcess(GameWorld world, GameLogger gameLogger);

    @Data
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class ScenarioScript {
        String speaker;
        String text;
        String image;
    }

    @AllArgsConstructor
    public static class Speaker {
        private String name;
        private final String image;

        ScenarioScript speak(String text) {
            return new ScenarioScript(name, text, image);
        }

        Speaker changeName(String name) {
            this.name = name;
            return this;
        }
    }
}
