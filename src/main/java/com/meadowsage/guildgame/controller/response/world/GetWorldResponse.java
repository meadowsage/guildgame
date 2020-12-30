package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.person.ApplicantReviewer;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.system.GameLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetWorldResponse {
    ResponseWorld world;
    ResponseGuild guild;
    List<ResponseGameLog> gameLogs;
    List<ResponseAdventurer> adventurers;
    List<ResponseApplicant> applicants;
    List<ResponseScenario> scenarios;

    public GetWorldResponse(GameWorld world, List<GameLog> gameLogs, List<Scenario> scenarios) {
        this.world = ResponseWorld.builder()
                .id(world.getId())
                .gameDate(world.getGameDate())
                .state(world.getState().name()).build();

        this.guild = ResponseGuild.builder()
                .money(world.getGuild().getMoney().getValue())
                .reputation(world.getGuild().getReputation()).build();

        this.gameLogs = gameLogs.stream()
                .map(gameLog -> new ResponseGameLog(gameLog.getMessage()))
                .collect(Collectors.toList());

        this.adventurers = world.getAdventurers().stream()
                .sorted(Comparator.comparing(Person::getId))
                .map(person -> ResponseAdventurer.builder()
                        .id(person.getId())
                        .name(person.getName().getFirstName())
                        .fullName(person.getName().getFullName())
                        .money(person.getMoney().getValue())
                        .reputation(person.getReputation().getValue())
                        .battle(person.getBattle().getValue())
                        .knowledge(person.getKnowledge().getValue())
                        .support(person.getSupport().getValue())
                        .energy(person.getEnergy().getValue())
                        .maxEnergy(person.getEnergy().getMax())
                        .build()
                ).collect(Collectors.toList());

        this.applicants = world.getApplicants().stream()
                .map(person -> ResponseApplicant.builder()
                        .id(person.getId())
                        .name(person.getName().getFirstName())
                        .fullName(person.getName().getFullName())
                        .remarks(ApplicantReviewer.of().review(person))
                        .build()
                ).collect(Collectors.toList());

        this.scenarios = scenarios.stream().map(scenario -> ResponseScenario.builder()
                .id(scenario.getId())
                .title(scenario.getTitle())
                .contents(scenario.getContents().stream()
                        .map(content -> ResponseScenarioContent.builder()
                                .speaker(content.getSpeaker())
                                .text(content.getText())
                                .image(content.getImage()).build()
                        ).collect(Collectors.toList())
                ).build()
        ).collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class ResponseWorld {
        long id;
        int gameDate;
        String state;
    }

    @Builder
    @Getter
    private static class ResponseGuild {
        long money;
        int reputation;
    }

    @AllArgsConstructor
    @Getter
    private static class ResponseGameLog {
        String message;
    }

    @Builder
    @Getter
    private static class ResponseAdventurer {
        long id;
        String name;
        String fullName;
        long money;
        long reputation;
        int battle;
        int knowledge;
        int support;
        int energy;
        int maxEnergy;
    }

    @Builder
    @Getter
    private static class ResponseApplicant {
        long id;
        String name;
        String fullName;
        List<String> remarks;
    }

    @Builder
    @Getter
    private static class ResponseScenario {
        int id;
        String title;
        List<ResponseScenarioContent> contents;
    }

    @Builder
    @Getter
    private static class ResponseScenarioContent {
        String speaker;
        String text;
        String image;
    }
}
