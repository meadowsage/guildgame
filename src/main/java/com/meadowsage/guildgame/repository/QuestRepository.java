package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.QuestMapper;
import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestRepository {

    private final QuestMapper questMapper;
    private final QuestOrderMapper questOrderMapper;

    public List<Quest> getQuests(long worldId) {
        return questMapper.select(worldId, null);
    }

    public Optional<Quest> get(long questId) {
        List<Quest> result = questMapper.select(null, questId);
        if (result.isEmpty()) return Optional.empty();
        else return Optional.of(result.get(0));
    }

    public void save(Quest quest, long worldId) {
        if (quest.isNew()) questMapper.insert(quest, worldId);
    }

    public void saveQuestOrder(QuestOrder questOrder) {
        if (questOrder.isNew()) questOrderMapper.insert(questOrder);
        questOrder.getNewProgress().ifPresent(questOrderProgress ->
                questOrderMapper.insertProgress(questOrderProgress, questOrder.getId()));
        questOrder.getResult().ifPresent(questOrderResult ->
                questOrderMapper.insertResult(questOrderResult, questOrder.getId()));
    }

    /**
     * 継続中の受注一覧を取得
     */
    public List<QuestOrder> getActiveQuestOrders(long worldId) {
        return questOrderMapper.select(worldId, true, null, null, null);
    }

    /**
     * 実行可能なクエストのうち、クエストIDが最も小さいものを１件取得
     */
    public Optional<QuestOrder> getNextQuestOrder(long worldId, int gameDate) {
        return questOrderMapper.select(worldId, true, null, gameDate, null)
                .stream().findFirst();
    }

    /**
     * 最後に実行されたクエストを取得
     * クエストID順に実行されていることを前提に、IDが最も大きいものを返す
     */
    public Optional<QuestOrder> getLastProcessedQuestOrder(long worldId, int gameDate) {
        return questOrderMapper.select(worldId, null, gameDate, null, null)
                .stream().max(Comparator.comparing(QuestOrder::getQuestId));
    }

    /**
     * 指定した日付に完了したクエストを取得
     */
    public List<QuestOrder> getCompletedQuestOrders(long worldId, int gameDate) {
        return questOrderMapper.select(worldId, null, null, null,  gameDate);
    }

    public void addQuestOrder(long questId, long partyId) {
        questOrderMapper.insert(new QuestOrder(questId, partyId));
    }

    public void cancelQuestOrder(long questOrderId) {
        questOrderMapper.delete(questOrderId);
    }
}
