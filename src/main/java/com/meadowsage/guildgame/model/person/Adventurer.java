package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.world.GameWorld;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Adventurer extends Person {

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
                    gameLogger.info(getName().getFirstName() + "は何もしなかった。", this, null);
                    break;
                case 2:
                    gameLogger.info(getName().getFirstName() + "は１人で鍛錬をした。", this, null);
                    break;
                case 3:
                    gameLogger.info(getName().getFirstName() + "は宿で瞑想をした。", this, null);
                    break;
                case 4:
                    gameLogger.info(getName().getFirstName() + "は酒場で朝まで騒いだ。", this, null);
                    break;
                case 5:
                    gameLogger.info(getName().getFirstName() + "はどこにいるのだろうか…", this, null);
                    break;
                case 6:
                default:
                    gameLogger.info(getName().getFirstName() + "は書物を読んで過ごした。", this, null);
                    break;
            }
        }
    }

    public void doMorningActivity(GameWorld world) {
    }

    /**
     * 報酬額の計算
     *
     * @param quest 対象クエスト
     * @return 金額
     */
    public int calcReward(Quest quest) {
        double base = 100 + getReputation().getValue() * 2;
        return 100 + (int) Math.round(base * (quest.getDanger() * 100));
    }

    public void earnMoney(Money money) {
        getMoney().add(money);
    }

    public void payMoney(Money money) {
        getMoney().subtract(money);
    }

    public Attribute getAttribute(Attribute.Type type) {
        switch (type) {
            case BATTLE:
                return getBattle();
            case KNOWLEDGE:
                return getKnowledge();
            case SUPPORT:
                return getSupport();
            default:
                throw new IllegalStateException();
        }
    }

    public Optional<PersonSkill> getSkill(Skill skill) {
        return getSkills().stream().filter(_skill -> _skill.getSkill().equals(skill)).findAny();
    }
}
