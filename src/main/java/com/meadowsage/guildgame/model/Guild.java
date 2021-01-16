package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.accounting.Treasurer;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.quest.Quest;
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
            List<Adventurer> adventurers,
            int gameDate,
            GameLogger gameLogger,
            Treasurer treasurer
    ) {
        // 成功したクエストを抽出
//        quests.stream().filter(Quest::isSucceeded).forEach(quest -> {
            // クエスト報酬の受け取り
//            treasurer.addQuestIncome(QuestIncome.process(quest, this, gameDate, gameLogger));
            // 冒険者への支払い
//            quest.getQuestOrders().stream().filter(QuestOrder::isSucceeded).forEach(questOrder -> {
//                adventurers.stream().filter(adventurer -> adventurer.getId() == questOrder.getPersonId())
//                        .findAny()
//                        .ifPresent(adventurer -> treasurer.addQuestPayment(
//                                QuestPayment.process(quest, questOrder, adventurer, this, gameDate, gameLogger))
//                        );
//            });
            // 名声の取得
//            int gainedReputation = 1 + (quest.getReward().getValue() / 100 + quest.getDanger() * 2) / 10;
//            reputation += gainedReputation;
//            gameLogger.info(quest.getName() + "の達成により名声" + gainedReputation + "を獲得した。");
//        });
        // TODO 維持費
//        treasurer.addFacilityPayment(FacilityPayment.process("事務所", 500, this, gameDate, gameLogger));
        // 残高を保存
//        treasurer.setGuildBalance(new GuildBalance(worldId, money.getValue(), gameDate));
    }

    public void earnMoney(Money money) {
        this.money.add(money);
    }

    public void payMoney(Money reward) {
        this.money.subtract(reward);
    }
}
