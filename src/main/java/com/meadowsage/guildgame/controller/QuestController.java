package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetQuestsResponse;
import com.meadowsage.guildgame.usecase.party.GetFreePartiesUseCase;
import com.meadowsage.guildgame.usecase.quest.GetQuestsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/{saveDataId}/worlds/{worldId}/quests")
public class QuestController {

    private final GetQuestsUseCase getQuestsUseCase;
    private final GetFreePartiesUseCase getFreePartiesUseCase;

    @GetMapping
    @ResponseBody
    @Transactional
    public GetQuestsResponse getQuests(
            @PathVariable String saveDataId,
            @PathVariable long worldId
    ) {
        // TODO 入力チェック
        System.out.println(saveDataId + " " + worldId);

        GetQuestsUseCase.GetQuestsUseCaseResult result = getQuestsUseCase.run(worldId);

        return new GetQuestsResponse(result.getQuests(), result.getQuestOrders(), result.getParties());
    }
}