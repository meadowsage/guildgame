package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.quest.QuestOrderProgress;
import com.meadowsage.guildgame.model.quest.QuestOrderResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestOrderMapper {
    void insert(QuestOrder questOrder);

    void insertProgress(QuestOrderProgress progress, long questOrderId);

    void insertResult(QuestOrderResult result, long questOrderId);

    List<QuestOrder> select(long worldId, Integer gameDate, boolean isActive);

    void update(long id, QuestOrder.State state);

    void delete(long id);
}
