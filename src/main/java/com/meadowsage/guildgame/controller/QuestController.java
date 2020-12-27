package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.controller.response.GetAdventurerRewardsResponse;
import com.meadowsage.guildgame.controller.response.GetOngoingQuestsResponse;
import com.meadowsage.guildgame.usecase.quest.GetOngoingQuestsUseCase;
import com.meadowsage.guildgame.usecase.quest.GetQuestOrderablesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ゲーム世界に対する操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{saveDataId}/world/{worldId}/quests")
public class QuestController {

    private final GetOngoingQuestsUseCase getOngoingQuestsUseCase;
    private final GetQuestOrderablesUseCase getQuestOrderablesUseCase;

    @GetMapping("/ongoing")
    @ResponseBody
    @Transactional
    public GetOngoingQuestsResponse getOngoingQuests(
            @PathVariable String saveDataId,
            @PathVariable long worldId
    ) {
        // TODO 入力チェック
        System.out.println(saveDataId + " " + worldId);

        GetOngoingQuestsUseCase.GetQuestsUseCaseResult result = getOngoingQuestsUseCase.run(worldId);

        return new GetOngoingQuestsResponse(result.getQuests(), result.getAdventurers());
    }

    @GetMapping("/{questId}/orderables")
    @ResponseBody
    @Transactional
    public List<GetAdventurerRewardsResponse> estimateAdventurerRewards(
            @PathVariable String saveDataId,
            @PathVariable long worldId,
            @PathVariable long questId
    ) {
        // TODO 入力チェック
        System.out.println(saveDataId + " " + worldId);

        GetQuestOrderablesUseCase.GetQuestOrderablesUseCaseResult result
                = getQuestOrderablesUseCase.run(worldId, questId);

        return result.getRewardsByPerson().entrySet().stream()
                .map(entry -> new GetAdventurerRewardsResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}