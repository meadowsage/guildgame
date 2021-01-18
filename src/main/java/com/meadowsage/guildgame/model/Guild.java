package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.accounting.*;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Guild {
    @Getter
    private Money money;
    @Getter
    private int reputation;

    public static Guild create() {
        Guild guild = new Guild();
        guild.money = Money.of(1000);
        guild.reputation = 10;
        return guild;
    }

    public void questAccounting(
            long worldId,
            List<Quest> quests,
            List<QuestOrder> questOrders,
            List<Party> parties,
            int gameDate,
            GameLogger gameLogger,
            Treasurer treasurer
    ) {
        // 成功したクエストを抽出
        questOrders.stream().filter(QuestOrder::isSucceeded).forEach(questOrder -> {
            Quest quest = quests.stream().filter(q -> q.getId() == questOrder.getQuestId()).findAny()
                    .orElseThrow(IllegalStateException::new);
            Party party = parties.stream().filter(p -> p.getId() == questOrder.getPartyId()).findAny()
                    .orElseThrow(IllegalStateException::new);

            // クエスト報酬の受け取り
            treasurer.addQuestIncome(QuestIncome.process(quest, this, gameDate, gameLogger));

            // 冒険者への支払い
            treasurer.addQuestPayments(QuestPayment.process(quest, questOrder, party, this, gameDate, gameLogger));

            // 名声の取得
            int gainedReputation = 1 + (quest.getReward().getValue() / 100 + quest.getDanger() * 2) / 10;
            reputation += gainedReputation;
            gameLogger.info(quest.getName() + "の達成により名声" + gainedReputation + "を獲得した。");
        });
        // TODO 維持費
        treasurer.addFacilityPayment(FacilityPayment.process("事務所", 500, this, gameDate, gameLogger));
        // 残高を保存
        treasurer.setGuildBalance(new GuildBalance(worldId, money.getValue(), gameDate));
    }

    public void earnMoney(Money money) {
        this.money.add(money);
    }

    public void payMoney(Money reward) {
        this.money.subtract(reward);
    }
}
