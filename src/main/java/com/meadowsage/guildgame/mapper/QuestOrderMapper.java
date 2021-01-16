package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.quest.QuestOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestOrderMapper {
    void insert(QuestOrder questOrder);

    List<QuestOrder> selectAll(long worldId);

    void update(long id, QuestOrder.State state);

    void delete(long id);
}
