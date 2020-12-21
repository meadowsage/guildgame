package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.request.AddQuestOrderRequest;
import com.meadowsage.guildgame.controller.request.CancelQuestOrderRequest;
import com.meadowsage.guildgame.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/quest")
public class QuestController {

    private final QuestService questService;

    @PutMapping("/{questId}/order")
    @ResponseBody
    @Transactional
    public void addQuestOrder(
            @RequestBody AddQuestOrderRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long questId
    ) {
        // TODO 入力チェック セーブデータとクエスト、冒険者の突き合わせ
        System.out.println(saveDataId + worldId);
        questService.addQuestOrder(questId, request.getPersonId());
    }

    @PostMapping("/{questId}/order")
    @ResponseBody
    @Transactional
    public void cancelQuestOrder(
            @RequestBody CancelQuestOrderRequest request,
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long questId
    ) {
        // TODO 入力チェック セーブデータとクエスト、冒険者の突き合わせ
        System.out.println(saveDataId + worldId);
        questService.cancelQuestOrder(questId, request.getPersonId());
    }
}