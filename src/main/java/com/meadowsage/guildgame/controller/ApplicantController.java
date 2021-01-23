package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.UpdateApplicantRequest;
import com.meadowsage.guildgame.controller.response.GetApplicantsResponse;
import com.meadowsage.guildgame.usecase.person.UpdateApplicantUseCase;
import com.meadowsage.guildgame.usecase.person.GetApplicantsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/applicants")
public class ApplicantController {

    private final GetApplicantsUseCase getApplicantsUseCase;
    private final UpdateApplicantUseCase updateApplicantUseCase;

    @GetMapping
    @Transactional(readOnly = true)
    public GetApplicantsResponse getAdventurers(@PathVariable String saveDataId, @PathVariable long worldId) {
        System.out.println(saveDataId + " " + worldId);
        return new GetApplicantsResponse(getApplicantsUseCase.run(worldId).getApplicants());
    }

    @PostMapping("/{applicantId}")
    @Transactional
    public void updateApplicant(
            @RequestBody UpdateApplicantRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long applicantId
    ) {
        // TODO 入力チェック セーブデータと応募者の突き合わせ、支給品が使用可能かのチェック（ギルドランクが基準）
        System.out.println(saveDataId + " " + worldId);
        updateApplicantUseCase.run(worldId, applicantId, request.getIsApproval());
    }
}