package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.AddPartyMemberRequest;
import com.meadowsage.guildgame.controller.request.CreatePartyRequest;
import com.meadowsage.guildgame.controller.request.UpdatePartyNameRequest;
import com.meadowsage.guildgame.controller.response.GetFreePartiesResponse;
import com.meadowsage.guildgame.controller.response.GetPartiesResponse;
import com.meadowsage.guildgame.controller.response.GetRandomPartyNameResponse;
import com.meadowsage.guildgame.usecase.party.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/parties")
public class PartyController {

    private final GetPartiesUseCase getPartiesUseCase;
    private final CreatePartyUseCase createPartyUseCase;
    private final AddPartyMemberUseCase addPartyMemberUseCase;
    private final RemovePartyMemberUseCase removePartyMemberUseCase;
    private final GetFreePartiesUseCase getFreePartiesUseCase;
    private final UpdatePartyNameUseCase updatePartyNameUseCase;
    private final GetRandomPartyNameUseCase getRandomPartyNameUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetPartiesResponse getParties(@PathVariable String saveDataId, @PathVariable long worldId) {
        System.out.println(saveDataId + " " + worldId);
        return new GetPartiesResponse(getPartiesUseCase.run(worldId));
    }

    @PutMapping
    @Transactional
    public void createParty(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @RequestBody CreatePartyRequest request
    ) {
        System.out.println(saveDataId + " " + worldId);
        createPartyUseCase.run(worldId, request.getName(), request.getMemberIds());
    }


    @PostMapping("/{partyId}/members")
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

    @DeleteMapping("/{partyId}/members/{personId}")
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

    @GetMapping("/free")
    @ResponseBody
    @Transactional(readOnly = true)
    public GetFreePartiesResponse getFreeParties(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @RequestParam long questId
    ) {
        // TODO 入力チェック
        System.out.println(saveDataId + " " + worldId);
        GetFreePartiesUseCase.GetFreePartiesUseCaseResult result = getFreePartiesUseCase.run(worldId, questId);
        return new GetFreePartiesResponse(result.getParties(), result.getQuest());
    }

    @PostMapping("/{partyId}/name")
    @Transactional
    public void updatePartyName(
            @PathVariable long partyId,
            @RequestBody UpdatePartyNameRequest request
    ) {
        // TODO 入力チェック
        updatePartyNameUseCase.run(partyId, request.getPartyName());
    }

    @GetMapping("/randomName")
    @Transactional(readOnly = true)
    public GetRandomPartyNameResponse getRandomPartyName() {
        return new GetRandomPartyNameResponse(getRandomPartyNameUseCase.run());
    }
}