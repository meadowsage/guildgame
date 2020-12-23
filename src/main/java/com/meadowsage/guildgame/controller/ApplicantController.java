package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.UpdateApplicantRequest;
import com.meadowsage.guildgame.usecase.UpdateApplicantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/applicant")
public class ApplicantController {

    private final UpdateApplicantUseCase updateApplicantUseCase;

    @PostMapping("/{applicantId}")
    @ResponseBody
    @Transactional
    public void updateApplicant(
            @RequestBody UpdateApplicantRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long applicantId
    ) {
        // TODO 入力チェック セーブデータと応募者の突き合わせ、支給品が使用可能かのチェック（ギルドランクが基準）
        System.out.println(saveDataId + " " + worldId);
        updateApplicantUseCase.run(applicantId, request.getIsApproval());
    }
}