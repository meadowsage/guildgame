package com.meadowsage.guildgame.model.accounting;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestIncome {
    private final int value;
    private final long questId;
    private final int gameDate;

    public static void process(
            Quest quest,
            Guild guild,
            int gameDate,
            GameLogger gameLogger,
            AccountingLogger accountingLogger
    ) {
        QuestIncome questIncome = new QuestIncome(quest.getReward().getValue(), quest.getId(), gameDate);

        guild.earnMoney(quest.getReward());
        gameLogger.detail(quest.getName() + "の報酬として" + quest.getReward().getValue() + "Gを獲得した。");

        accountingLogger.recordQuestIncome(questIncome);
    }
}
