package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.person.Person;
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

    public GetWorldResponse(World world, List<GameLog> gameLogs) {
        this.world = new ResponseWorld();
        this.world.id = world.getId();
        this.world.gameDate = world.getGameDate();

        this.guild = new ResponseGuild();
        this.guild.money = world.getGuild().getMoney().getValue();
        this.guild.reputation = world.getGuild().getReputation();

        this.gameLogs = gameLogs.stream().map(_gameLog -> {
            ResponseGameLog gameLog = new ResponseGameLog();
            gameLog.setMessage(_gameLog.getMessage());
            return gameLog;
        }).collect(Collectors.toList());

        this.adventurers = world.getPersons().stream()
                .filter(Person::isAdventurer)
                .sorted(Comparator.comparing(Person::getId))
                .map(_person -> {
                    ResponseAdventurer adventurer = new ResponseAdventurer();
                    adventurer.setId(_person.getId());
                    adventurer.setName(_person.getName().getFirstName());
                    adventurer.setFullName(_person.getName().getFullName());
                    adventurer.setMoney(_person.getMoney().getValue());
                    adventurer.setReputation(_person.getReputation().getValue());
                    adventurer.setBattle(_person.getBattle());
                    adventurer.setKnowledge(_person.getKnowledge());
                    adventurer.setSupport(_person.getSupport());
                    adventurer.setEnergy((int) _person.getEnergy().getValue());
                    adventurer.setMaxEnergy(_person.getMaxEnergy());
                    return adventurer;
                }).collect(Collectors.toList());

        this.applicants = world.getPersons().stream()
                .filter(Person::isApplicant)
                .map(_person -> {
                    ResponseApplicant applicant = new ResponseApplicant();
                    applicant.setId(_person.getId());
                    applicant.setName(_person.getName().getFirstName());
                    applicant.setFullName(_person.getName().getFullName());
                    applicant.setRemarks(_person.createRemarks());
                    return applicant;
                }).collect(Collectors.toList());

        this.quests = world.getQuests().stream().map(_quest -> {
            ResponseQuest quest = new ResponseQuest();
            quest.setId(_quest.getId());
            quest.setDifficulty(_quest.getDifficulty());
            quest.setType(_quest.getType().name());
            quest.setReservedBy(this.adventurers.stream()
                    .filter(adventurer -> adventurer.getId() == _quest.getReservedBy())
                    .findAny().orElse(null));
            return quest;
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
        int difficulty;
        ResponseAdventurer reservedBy;
    }
}
