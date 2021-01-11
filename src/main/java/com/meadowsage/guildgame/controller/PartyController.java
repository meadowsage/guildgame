package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetPartiesResponse;
import com.meadowsage.guildgame.usecase.GetPartiesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/parties")
public class PartyController {

    private final GetPartiesUseCase getPartiesUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetPartiesResponse getParties(@PathVariable String saveDataId, @PathVariable long worldId) {
        System.out.println(saveDataId + " " + worldId);
        return new GetPartiesResponse(getPartiesUseCase.run(worldId));
    }
}