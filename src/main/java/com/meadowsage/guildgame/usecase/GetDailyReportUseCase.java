package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.model.accounting.FacilityPayment;
import com.meadowsage.guildgame.model.accounting.GuildBalance;
import com.meadowsage.guildgame.model.accounting.QuestIncome;
import com.meadowsage.guildgame.model.accounting.QuestPayment;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.AccountingRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetDailyReportUseCase {
    private final QuestRepository questRepository;
    private final PersonRepository personRepository;
    private final AccountingRepository accountingRepository;

    public GetDailyReportUseCaseResult run(long worldId, int gameDate) {
        List<QuestIncome> questIncomes = accountingRepository.getQuestIncomes(worldId, gameDate);
        List<QuestPayment> questPayments = accountingRepository.getQuestPayments(worldId, gameDate);
        List<FacilityPayment> facilityPayments = accountingRepository.getFacilityPayments(worldId, gameDate);
        GuildBalance lastGuildBalance = accountingRepository.getBalance(worldId, gameDate - 1);
        GuildBalance guildBalance = accountingRepository.getBalance(worldId, gameDate);

        GetDailyReportUseCaseResult result = new GetDailyReportUseCaseResult();

        // 前回残高
        if (lastGuildBalance != null) {
            result.addItem("前回残高", null, null, lastGuildBalance.getValue());
        }

        // クエスト報酬
        for (QuestIncome income : questIncomes) {
            Quest quest = questRepository.get(income.getQuestId()).orElseThrow(IllegalStateException::new);
            result.addItem(quest.getName() + " 達成報酬", income.getValue(), null, null);

            questPayments.stream()
                    .filter(payment -> payment.getQuestId() == income.getQuestId())
                    .forEach(payment -> {
                        Person person = personRepository.getAdventurer(payment.getPersonId())
                                .orElseThrow(IllegalStateException::new);
                        result.addItem(
                                "報酬支払 " + person.getName().getFirstName(),
                                null,
                                payment.getValue(),
                                null);
                    });
        }

        // 施設費
        for (FacilityPayment facilityPayment : facilityPayments) {
            result.addItem(
                    "施設維持費 " + facilityPayment.getName(),
                    null,
                    facilityPayment.getValue(),
                    null);
        }

        // 合計
        result.addItem(
                "計",
                questIncomes.stream().mapToInt(QuestIncome::getValue).sum(),
                questPayments.stream().mapToInt(QuestPayment::getValue).sum() +
                        facilityPayments.stream().mapToInt(FacilityPayment::getValue).sum(),
                null);

        // 残高
        result.addItem("残高", null, null, guildBalance.getValue());

        return result;
    }

    public static class GetDailyReportUseCaseResult {
        @Getter
        private final List<ReportItem> reportItems = new ArrayList<>();

        public void addItem(String description, Integer income, Integer outcome, Integer balance) {
            reportItems.add(new ReportItem(description, income, outcome, balance));
        }

        @AllArgsConstructor
        @Getter
        public static class ReportItem {
            private final String description;
            private final Integer income;
            private final Integer outcome;
            private final Integer balance;
        }
    }
}
