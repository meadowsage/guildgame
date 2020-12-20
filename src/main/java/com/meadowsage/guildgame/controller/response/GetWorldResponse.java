package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.scenario.Scenario;
import com.meadowsage.guildgame.model.system.GameLog;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetWorldResponse {
    ResponseWorld world;
    ResponseGuild guild;
    List<ResponseGameLog> gameLogs;
    List<ResponseAdventurer> adventurers;
    List<ResponseApplicant> applicants;
    List<ResponseQuest> quests;
    List<ResponseScenario> scenarios;

    public GetWorldResponse(World world, List<GameLog> gameLogs, List<Scenario> scenarios) {
        this.world = new ResponseWorld();
        this.world.id = world.getId();
        this.world.gameDate = world.getGameDate();

        this.guild = new ResponseGuild();
        this.guild.money = world.getGuild().getMoney().getValue();
        this.guild.reputation = world.getGuild().getReputation();

        this.gameLogs = gameLogs.stream().map(gameLog -> {
            ResponseGameLog res = new ResponseGameLog();
            res.setMessage(gameLog.getMessage());
            return res;
        }).collect(Collectors.toList());

        this.adventurers = world.getPersons().stream()
                .filter(Person::isAdventurer)
                .sorted(Comparator.comparing(Person::getId))
                .map(person -> {
                    ResponseAdventurer res = new ResponseAdventurer();
                    res.setId(person.getId());
                    res.setName(person.getName().getFirstName());
                    res.setFullName(person.getName().getFullName());
                    res.setMoney(person.getMoney().getValue());
                    res.setReputation(person.getReputation().getValue());
                    res.setBattle(person.getBattle());
                    res.setKnowledge(person.getKnowledge());
                    res.setSupport(person.getSupport());
                    res.setEnergy((int) person.getEnergy().getValue());
                    res.setMaxEnergy(person.getMaxEnergy());
                    return res;
                }).collect(Collectors.toList());

        this.applicants = world.getPersons().stream()
                .filter(Person::isApplicant)
                .map(person -> {
                    ResponseApplicant res = new ResponseApplicant();
                    res.setId(person.getId());
                    res.setName(person.getName().getFirstName());
                    res.setFullName(person.getName().getFullName());
                    res.setRemarks(person.createRemarks());
                    return res;
                }).collect(Collectors.toList());

        this.quests = world.getQuests().stream().map(quest -> {
            ResponseQuest res = new ResponseQuest();
            res.setId(quest.getId());
            res.setName(quest.getName());
            res.setDifficulty(quest.getDifficulty());
            res.setType(quest.getType().name());
            res.setReservedBy(this.adventurers.stream()
                    .filter(adventurer -> quest.getReservedBy().contains(adventurer.getId()))
                    .collect(Collectors.toList()));
            return res;
        }).collect(Collectors.toList());

        this.scenarios = scenarios.stream().map(scenario -> {
            ResponseScenario res = new ResponseScenario();
            res.id = scenario.getId();
            res.title = scenario.getTitle();
            res.contents = scenario.getContents().stream().map(content -> {
                ResponseScenarioContent resContent = new ResponseScenarioContent();
                resContent.speaker = content.getSpeaker();
                resContent.text = content.getText();
                resContent.image = content.getImage();
                return resContent;
            }).collect(Collectors.toList());
            return res;
        }).collect(Collectors.toList());
    }

    @Data
    private static class ResponseWorld {
        long id;
        int gameDate;
    }

    @Data
    private static class ResponseGuild {
        long money;
        int reputation;
    }

    @Data
    private static class ResponseGameLog {
        String message;
    }

    @Data
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

    @Data
    private static class ResponseApplicant {
        long id;
        String name;
        String fullName;
        List<String> remarks;
    }

    @Data
    private static class ResponseQuest {
        long id;
        String type;
        String name;
        int difficulty;
        List<ResponseAdventurer> reservedBy;
    }

    @Data
    private static class ResponseScenario {
        int id;
        String title;
        List<ResponseScenarioContent> contents;
    }

    @Data
    private static class ResponseScenarioContent {
        String speaker;
        String text;
        String image;
    }
}
