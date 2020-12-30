package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GuildBalanceMapper;
import com.meadowsage.guildgame.mapper.QuestIncomeMapper;
import com.meadowsage.guildgame.mapper.QuestPaymentMapper;
import com.meadowsage.guildgame.model.accounting.GuildBalance;
import com.meadowsage.guildgame.model.accounting.QuestIncome;
import com.meadowsage.guildgame.model.accounting.QuestPayment;
import com.meadowsage.guildgame.model.accounting.Treasurer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TreasurerRepository {
    private final QuestPaymentMapper questPaymentMapper;
    private final QuestIncomeMapper questIncomeMapper;
    private final GuildBalanceMapper guildBalanceMapper;

    public void save(Treasurer treasurer) {
        treasurer.getQuestPayments().forEach(questPaymentMapper::insert);
        treasurer.getQuestIncomes().forEach(questIncomeMapper::insert);
        treasurer.getGuildBalance().ifPresent(guildBalanceMapper::insert);
    }

    public List<QuestPayment> getQuestPayments(long worldId, int gameDate) {
        return questPaymentMapper.select(worldId, gameDate);
    }

    public List<QuestIncome> getQuestIncomes(long worldId, int gameDate) {
        return questIncomeMapper.select(worldId, gameDate);
    }

    public GuildBalance getBalance(long worldId, int gameDate) {
        return guildBalanceMapper.select(worldId, gameDate);
    }
}
