package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponseAdventurer;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetAdventurersResponse {
    List<ResponseAdventurer> adventurers;

    public GetAdventurersResponse(List<Adventurer> adventurers) {
        this.adventurers = adventurers.stream()
                .sorted(Comparator.comparing(Person::getId))
                .map(ResponseAdventurer::new).collect(Collectors.toList());
    }
}
