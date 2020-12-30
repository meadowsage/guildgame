package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.accounting.QuestPayment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestPaymentMapper {
    void insert(QuestPayment questPayment);
    List<QuestPayment> select(long worldId, int gameDate);
}
