package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.AddQuestOrderRequest;
import com.meadowsage.guildgame.usecase.AddQuestOrderUseCase;
import com.meadowsage.guildgame.usecase.CancelQuestOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/questOrder")
public class QuestOrderController {

    private final CancelQuestOrderUseCase cancelQuestOrderUseCase;
    private final AddQuestOrderUseCase addQuestOrderUseCase;

    @PutMapping("")
    @Transactional
    public void addQuestOrder(
            @RequestBody AddQuestOrderRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId
    ) {
        // TODO 入力チェック セーブデータとクエスト、冒険者の突き合わせ
        System.out.println(saveDataId + worldId);
        addQuestOrderUseCase.run(request.getQuestId(), request.getPersonId());
    }

    @DeleteMapping("/{questOrderId}")
    @Transactional
    public void cancelQuestOrder(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long questOrderId
    ) {
        // TODO 入力チェック セーブデータとクエスト、冒険者の突き合わせ
        System.out.println(saveDataId + worldId);
        cancelQuestOrderUseCase.run(questOrderId);
    }
}