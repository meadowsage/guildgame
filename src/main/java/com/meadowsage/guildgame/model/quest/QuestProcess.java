package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;

/**
 * クエストの処理
 */
public class QuestProcess {
    // 目標値 基本・最大・最小
    private static final int TARGET_BASE = 75;
    private static final int TARGET_MAX = 90;
    private static final int TARGET_MIN = 5;
    // 補正値 必須不足、推奨充足、疲労時
    private static final int MODIFIER_REQUIREMENTS_UNSATISFIED = -25;
    private static final int MODIFIER_RECOMMENDS_SATISFIED = 10;
    private static final int MODIFIER_IS_TIRED = -25;

    Quest quest;
    QuestOrder questOrder;
    Party party;
    int gameDate;

    public QuestProcess(Quest quest, QuestOrder questOrder, Party party, int gameDate) {
        this.quest = quest;
        this.questOrder = questOrder;
        this.party = party;
        this.gameDate = gameDate;
    }

    public void run(Dice dice, GameLogger gameLogger) {
        gameLogger.detail(party.getName() + "が" + quest.getName() + "を開始した。", null, quest);

        // 目標値
        // 必須条件が１つ足りないごとに目標値-25%
        int target = TARGET_BASE - quest.numberOfUnsatisfiedRequirements(party) * MODIFIER_REQUIREMENTS_UNSATISFIED;
        target = Math.max(target, TARGET_MIN);
        target = Math.min(target, TARGET_MAX);

        // パーティ全員が成否判定を行い、成功度を合算
        int successPoint = 0;
        for (Adventurer member : party.getMembers()) {
            successPoint += tryQuest(member, dice, gameLogger, target);
        }

        // 完了判定
        boolean isCompleted = checkIfCompleted(successPoint);

        // 報酬判定
        if (isCompleted) {
            success(gameLogger);
        } else {
            // TODO リタイア判定
            // とりあえず、全員の体力が尽きたら失敗
            if (party.getMembers().stream().allMatch(Person::isTired)) {
                failure(gameLogger);
            } else {
                gameLogger.info("[継続] " + party.getName() + "は野営して過ごした。", null, quest);
                questOrder.saveProgress(gameDate, successPoint);
            }
        }

        // パーティメンバの行動済フラグをONにする
        party.getMembers().forEach(Person::setAsActioned);
    }

    private int tryQuest(Adventurer adventurer, Dice dice, GameLogger gameLogger, int target) {
        // 補正値
        int modifier = (adventurer.isTired() ? MODIFIER_IS_TIRED : 0)
                + (quest.numberOfSatisfiedRecommends(adventurer) * MODIFIER_RECOMMENDS_SATISFIED);

        Dice.DiceRollResult result = dice.calcResult(target, modifier);

        String resultMessage = adventurer.getName().getFirstName()
                + ": [1D100"
                + (modifier > 0 ? "+" + modifier : "")
                + (modifier < 0 ? modifier : "")
                + " <= " + target + "] → "
                + result.getNumber() + " " + result.getType().name();

        gameLogger.debug(resultMessage, adventurer, quest);

        // ダイスロールの結果に応じてイベントを実行
        switch (result.getType()) {
            case CRITICAL:
                return quest.criticalEvent(adventurer, gameLogger);
            case SPECIAL:
                return quest.specialEvent(adventurer, gameLogger);
            case SUCCESS:
                return quest.successEvent(adventurer, gameLogger);
            case FAILURE:
                return quest.failureEvent(adventurer, gameLogger);
            case FUMBLE:
                return quest.fumbleEvent(adventurer, gameLogger);
            default:
                return 0;
        }
    }

    private boolean checkIfCompleted(int successPoint) {
        return successPoint + questOrder.getTotalSuccessPoint() >= quest.getAmount();
    }

    private void success(GameLogger gameLogger) {
        // クエストを完了状態に変更
        questOrder.markAsSuccess(gameDate);

        // 報酬・経験点・名声を付与
        party.getMembers().forEach(person -> {
            person.getMoney().add(quest.getReward().getValue() / party.getMembers().size());
            person.getReputation().add(quest.calcReputation() / party.getMembers().size());
            // TODO 経験点の検討
            // 体力消費
            person.getEnergy().consume(1);
        });

        gameLogger.important("[成功] " + party.getName() + "が" + quest.getName() + "を完了した！", null, quest);
    }

    private void failure(GameLogger gameLogger) {
        gameLogger.fatal("[失敗] パーティ全員の体力が尽きたため帰還します。", null, quest);

        // 報酬・経験点・名声を付与
        party.getMembers().forEach(person -> {
            person.getMoney().add(quest.getReward().getValue() / party.getMembers().size());
            person.getReputation().add(quest.calcReputation() / party.getMembers().size());
            // TODO 経験点の検討
            // 体力消費
            person.getEnergy().consume(1);
        });

        questOrder.markAsFailure(gameDate);
    }
}
