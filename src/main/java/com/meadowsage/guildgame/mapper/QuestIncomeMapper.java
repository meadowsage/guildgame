package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.accounting.QuestIncome;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestIncomeMapper {
    void insert(QuestIncome questIncome);
    List<QuestIncome> select(long worldId, int gameDate);
}
