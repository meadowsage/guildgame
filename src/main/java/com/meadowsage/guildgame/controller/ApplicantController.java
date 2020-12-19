package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.ScreeningRequest;
import com.meadowsage.guildgame.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/applicant")
public class ApplicantController {

    private final PersonService personService;

    @PostMapping("/{applicantId}")
    @ResponseBody
    public void screening(
            @RequestBody ScreeningRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long applicantId
    ) {
        // TODO 入力チェック セーブデータと応募者の突き合わせ、支給品が使用可能かのチェック（ギルドランクが基準）
        personService.screening(applicantId, request.getIsApproval());
    }
}