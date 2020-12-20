package com.meadowsage.guildgame.model.scenario;

import lombok.AllArgsConstructor;

public abstract class ScenarioBase implements Scenario {
    @Override
    public int getId() {
        return Integer.parseInt(getClass().getName().replaceAll("[^0-9]+", ""));
    }

    @AllArgsConstructor
    static class Speaker {
        private String name;
        private final String image;

        ScenarioContent speak(String text) {
            return new ScenarioContent(name, text, image);
        }

        Speaker changeName(String name) {
            this.name = name;
            return this;
        }
    }
}
