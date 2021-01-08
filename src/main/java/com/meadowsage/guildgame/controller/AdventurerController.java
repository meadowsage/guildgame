package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetAdventurersResponse;
import com.meadowsage.guildgame.usecase.world.GetAdventurersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/adventurers")
public class AdventurerController {

    private final GetAdventurersUseCase getAdventurersUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetAdventurersResponse getAdventurers(@PathVariable String saveDataId, @PathVariable long worldId) {
        System.out.println(saveDataId + " " + worldId);
        return new GetAdventurersResponse(getAdventurersUseCase.run(worldId).getAdventurers());
    }
}