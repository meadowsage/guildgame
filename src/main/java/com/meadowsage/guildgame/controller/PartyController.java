package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.AddPartyMemberRequest;
import com.meadowsage.guildgame.controller.response.GetPartiesResponse;
import com.meadowsage.guildgame.usecase.person.AddPartyMemberUseCase;
import com.meadowsage.guildgame.usecase.person.GetPartiesUseCase;
import com.meadowsage.guildgame.usecase.person.RemovePartyMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/parties")
public class PartyController {

    private final GetPartiesUseCase getPartiesUseCase;
    private final AddPartyMemberUseCase addPartyMemberUseCase;
    private final RemovePartyMemberUseCase removePartyMemberUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetPartiesResponse getParties(@PathVariable String saveDataId, @PathVariable long worldId) {
        System.out.println(saveDataId + " " + worldId);
        return new GetPartiesResponse(getPartiesUseCase.run(worldId));
    }

    @PostMapping("/{partyId}/partyMembers")
    @Transactional
    public void addPartyMember(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long partyId,
            @RequestBody AddPartyMemberRequest addPartyMemberRequest
    ) {
        System.out.println(saveDataId + " " + worldId);
        addPartyMemberUseCase.run(partyId, addPartyMemberRequest.getPersonId());
    }

    @DeleteMapping("/{partyId}/partyMembers/{personId}")
    @Transactional
    public void removePartyMember(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long partyId,
            @PathVariable long personId
    ) {
        System.out.println(saveDataId + " " + worldId);
        removePartyMemberUseCase.run(partyId, personId);
    }
}