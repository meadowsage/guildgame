package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.FacilityPaymentMapper;
import com.meadowsage.guildgame.mapper.GuildBalanceMapper;
import com.meadowsage.guildgame.mapper.QuestIncomeMapper;
import com.meadowsage.guildgame.mapper.QuestPaymentMapper;
import com.meadowsage.guildgame.model.accounting.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountingRepository {
    private final QuestPaymentMapper questPaymentMapper;
    private final QuestIncomeMapper questIncomeMapper;
    private final FacilityPaymentMapper facilityPaymentMapper;
    private final GuildBalanceMapper guildBalanceMapper;

    public void save(AccountingLogger accountingLogger) {
        accountingLogger.getQuestPayments().forEach(questPaymentMapper::insert);
        accountingLogger.getQuestIncomes().forEach(questIncomeMapper::insert);
        accountingLogger.getFacilityPayments().forEach(facilityPayment ->
                facilityPaymentMapper.insert(facilityPayment, accountingLogger.getWorldId()));
        accountingLogger.getGuildBalance().ifPresent(guildBalanceMapper::insert);
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

    public List<FacilityPayment> getFacilityPayments(long worldId, int gameDate) {
        return facilityPaymentMapper.select(worldId, gameDate);
    }
}
