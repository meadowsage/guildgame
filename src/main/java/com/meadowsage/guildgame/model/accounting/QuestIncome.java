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

    public static QuestIncome process(Quest quest, Guild guild, int gameDate, GameLogger gameLogger) {
        QuestIncome questIncome = new QuestIncome(
                quest.getRewards().getValue(),
                quest.getId(),
                gameDate);

        guild.earnMoney(quest.getRewards());
        gameLogger.detail(quest.getName() + "の報酬として" + quest.getRewards().getValue() + "Gを獲得した。");

        return questIncome;
    }
}
