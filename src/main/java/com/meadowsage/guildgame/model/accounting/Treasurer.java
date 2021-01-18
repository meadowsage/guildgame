package com.meadowsage.guildgame.model.accounting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 会計係クラス
 * 収支を記録して最後に保存する
 */
@Builder
public class Treasurer {
    @Getter
    private final long worldId;
    @Getter
    private final int gameDate;
    @Getter
    @Builder.Default
    private final List<QuestIncome> questIncomes = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<QuestPayment> questPayments = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<FacilityPayment> facilityPayments = new ArrayList<>();
    @Setter
    @Builder.Default
    private GuildBalance guildBalance = null;

    public Optional<GuildBalance> getGuildBalance() {
        return Optional.ofNullable(guildBalance);
    }

    public void addQuestIncome(QuestIncome questIncome) {
        questIncomes.add(questIncome);
    }

    public void addQuestPayments(List<QuestPayment> questPayments) {
        this.questPayments.addAll(questPayments);
    }

    public void addFacilityPayment(FacilityPayment facilityPayment) {
        facilityPayments.add(facilityPayment);
    }
}
