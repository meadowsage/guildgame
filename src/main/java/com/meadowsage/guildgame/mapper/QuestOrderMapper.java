package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.quest.QuestOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestOrderMapper {
    void insert(QuestOrder questOrder);

    void delete(long id);

    void update(long id, QuestOrder.State state);
}
