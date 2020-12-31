package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestPayment {
    private final int value;
    private final long questId;
    private final long personId;
    private final int gameDate;

    public static QuestPayment process(
            Quest quest,
            QuestOrder questOrder,
            Adventurer adventurer,
            Guild guild,
            int gameDate,
            GameLogger gameLogger
    ) {
        QuestPayment questIncome = new QuestPayment(
                questOrder.getRewards().getValue(),
                questOrder.getQuestId(),
                adventurer.getId(),
                gameDate);

        guild.payMoney(questOrder.getRewards());
        adventurer.earnMoney(questOrder.getRewards());
        gameLogger.detail(adventurer.getName().getFirstName() + "にクエスト報酬として"
                + questOrder.getRewards().getValue() + "Gを支払った。");

        return questIncome;
    }
}
