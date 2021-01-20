package com.meadowsage.guildgame.model.accounting;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountingLogger {
    @Getter
    private final long worldId;
    @Getter
    private final int gameDate;
    @Getter
    private final List<QuestIncome> questIncomes = new ArrayList<>();
    @Getter
    private final List<QuestPayment> questPayments = new ArrayList<>();
    @Getter
    private final List<FacilityPayment> facilityPayments = new ArrayList<>();

    private GuildBalance guildBalance = null;

    public AccountingLogger(long worldId, int gameDate) {
        this.worldId = worldId;
        this.gameDate = gameDate;
    }

    public Optional<GuildBalance> getGuildBalance() {
        return Optional.ofNullable(guildBalance);
    }

    public void recordGuildBalance(GuildBalance guildBalance) {
        this.guildBalance = guildBalance;
    }

    public void recordQuestIncome(QuestIncome questIncome) {
        questIncomes.add(questIncome);
    }

    public void recordQuestPayments(List<QuestPayment> questPayments) {
        this.questPayments.addAll(questPayments);
    }

    public void recordFacilityPayment(FacilityPayment facilityPayment) {
        facilityPayments.add(facilityPayment);
    }
}
