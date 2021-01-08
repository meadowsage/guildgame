package com.meadowsage.guildgame.controller.response.scenario;

import com.meadowsage.guildgame.model.scenario.Scenario;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetNextResponse {
    ResponseScenario scenario;

    public GetNextResponse(Scenario scenario) {
        if (scenario == null) return;
        this.scenario = ResponseScenario.builder()
                .id(scenario.getId())
                .title(scenario.getTitle())
                .scripts(scenario.getScripts().stream()
                        .map(content -> ResponseScenarioScript.builder()
                                .speaker(content.getSpeaker())
                                .text(content.getText())
                                .image(content.getImage()).build()
                        ).collect(Collectors.toList())
                ).build();
    }

    @Builder
    @Getter
    private static class ResponseScenario {
        String id;
        String title;
        List<ResponseScenarioScript> scripts;
    }

    @Builder
    @Getter
    private static class ResponseScenarioScript {
        String speaker;
        String text;
        String image;
    }
}
