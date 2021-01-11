package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestType;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Adventurer extends Person {
    private Long orderingQuestId;

    @Override
    public boolean isAdventurer() {
        return true;
    }

    @Override
    public void doDaytimeActivity(GameWorld world, GameLogger gameLogger) {
        if (!getEnergy().isFull()) {
            // 体力を消費している場合、休息を取る
            gameLogger.info(getName().getFirstName() + "は休息をとった。[体力回復]", this, null);
            getEnergy().recoverToMax();
        } else {
            // TODO クエスト中でないキャラクターの行動
            switch (new Dice().roll(1, 6)) {
                case 1:
                    gameLogger.warning(getName().getFirstName() + "は何もしなかった。[不満+]", this, null);
                    break;
                case 2:
                    gameLogger.warning(getName().getFirstName() + "は１人で鍛錬をした。[不満+]", this, null);
                    break;
                case 3:
                    gameLogger.warning(getName().getFirstName() + "は宿で瞑想をした。[不満+]", this, null);
                    break;
                case 4:
                    gameLogger.warning(getName().getFirstName() + "は酒場で朝まで騒いだ。[不満+]", this, null);
                    break;
                case 5:
                    gameLogger.warning(getName().getFirstName() + "はどこにいるのだろうか…[不満+]", this, null);
                    break;
                case 6:
                default:
                    gameLogger.warning(getName().getFirstName() + "は書物を読んで過ごした。[不満+]", this, null);
                    break;
            }
        }
    }

    public boolean isOrderingQuest() {
        return orderingQuestId != null;
    }

    public void doMorningActivity(GameWorld world) {
        if (!isOrderingQuest() && !isTired()) {
            // クエストを受注しておらず、体調に問題ない場合、クエストを受注する
            world.getAvailableQuests().stream().findFirst().ifPresent(quest -> quest.order(this));
        }
    }

    /**
     * 報酬額の計算
     *
     * @param quest 対象クエスト
     * @return 金額
     */
    public Money calcRewards(Quest quest) {
        double base = 100 + getReputation().getValue() * 2;
        return Money.of(100 + (int) Math.round(base * (quest.getDifficulty() * 0.1) + (quest.getDanger() * 100)));
    }

    /**
     * クエスト種別に対する能力の基本発揮値を算出
     *
     * @param questType 対象クエスト種別
     * @return 発揮値
     */
    public int getBasePerformance(QuestType questType) {
        return (int) (getBattle().getValue() * questType.getBattleCoefficient() +
                getKnowledge().getValue() * questType.getKnowledgeCoefficient() +
                getSupport().getValue() * questType.getSupportCoefficient()) / 3;
    }

    public void earnMoney(Money money) {
        getMoney().add(money);
    }

    public void payMoney(Money money) {
        getMoney().subtract(money);
    }
}
