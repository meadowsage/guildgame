package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestPayment {
    private final int value;
    private final long questId;
    private final long personId;
    private final int gameDate;

    public static void process(
            Quest quest,
            QuestOrder questOrder,
            Party party,
            Guild guild,
            int gameDate,
            GameLogger gameLogger,
            AccountingLogger accountingLogger
    ) {
        List<QuestPayment> payments = party.getMembers().stream().map(adventurer -> {
            Money payment = Money.of(adventurer.calcReward(quest));
            QuestPayment questIncome = new QuestPayment(payment.getValue(), questOrder.getQuestId(), adventurer.getId(), gameDate);
            guild.payMoney(payment);
            adventurer.earnMoney(payment);
            return questIncome;
        }).collect(Collectors.toList());

        accountingLogger.recordQuestPayments(payments);
    }
}
