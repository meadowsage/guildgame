package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLogger;
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

    public static List<QuestPayment> process(
            Quest quest,
            QuestOrder questOrder,
            Party party,
            Guild guild,
            int gameDate,
            GameLogger gameLogger
    ) {
        gameLogger.detail(party.getName() + "にクエスト報酬として" + quest.getReward().getValue() + "Gを支払った。");

        return party.getMembers().stream().map(adventurer -> {
            // パーティメンバ数で分割
            int payment = quest.getReward().getValue() / party.getMembers().size();
            QuestPayment questIncome = new QuestPayment(payment, questOrder.getQuestId(), adventurer.getId(), gameDate);
            guild.payMoney(quest.getReward());
            adventurer.earnMoney(quest.getReward());
            return questIncome;
        }).collect(Collectors.toList());
    }
}
