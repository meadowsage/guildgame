package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * クエストの処理
 */
public class QuestProcess {
    // 目標値 基本・最大・最小
    private static final int TARGET_BASE = 75;
    private static final int TARGET_MAX = 90;
    private static final int TARGET_MIN = 5;

    Quest quest;
    List<Adventurer> party;
    int gameDate;

    public QuestProcess(Quest quest, List<Adventurer> party, int gameDate) {
        this.quest = quest;
        this.party = party;
        this.gameDate = gameDate;
    }

    public void run(Dice dice, GameLogger gameLogger) {
        gameLogger.detail(party.stream()
                        .map(adventurer -> adventurer.getName().getFirstName())
                        .collect(Collectors.joining("、")) + "が" + quest.getName() + "を開始した。",
                null, quest);


        // パーティ全員が成否判定を行い、成功度を合算
        // TODO 発揮値順
        int successPoint = 0;

        for (Adventurer adventurer : party) {
            successPoint += tryQuest(adventurer, dice, gameLogger);
        }

        // TODO ランダムイベント

        // 完了判定
        boolean isCompleted = checkIfCompleted(successPoint);

        // 報酬判定
        if (isCompleted) {
            success(gameLogger);
        } else {
            // TODO リタイア判定
            // 体力がゼロになったメンバーはリタイア

            // 失敗判定
            // パーティメンバーが残っていないか、期日を超過した場合は失敗
            failure(gameLogger);
        }

        // 行動済フラグをONにする
        party.forEach(Person::setAsActioned);
    }

    private int tryQuest(Adventurer adventurer, Dice dice, GameLogger gameLogger) {
        // 発揮値を算出
        int performance = adventurer.getBasePerformance(quest.getType());

        // 疲労時の補正 TODO 発揮値算出に動かしたほうが良いかも…？もしくはbaseとcomputedを用意するか
        if(adventurer.isTired()) {
            gameLogger.warning(adventurer.getName().getFirstName() + "は疲労により力が出ない！", adventurer, quest);
            performance *= 0.5;
        }


        // 目標値：発揮値との差分で補正
        // 目標値に達していない場合はマイナス補正
        int modifier = (performance - quest.getDifficulty());
        if (modifier < 0) modifier -= 15;
        int target = TARGET_BASE + modifier;
        if (target > TARGET_MAX) target = TARGET_MAX;
        else if (target < TARGET_MIN) target = TARGET_MIN;

        gameLogger.debug(
                adventurer.getName().getFirstName() + ": 発揮値" + performance
                        + " / 難易度" + quest.getDifficulty() + " -> 補正値" + modifier,
                adventurer, quest);

        Dice.DiceRollResult result = dice.calcResult(target);

        String resultMessage = adventurer.getName().getFirstName() + ": [1D100 <= " + target + "] → " +
                result.getNumber() + " " + result.getType().name();
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
        return successPoint > 0;
    }

    private void success(GameLogger gameLogger) {
        // クエストを完了状態に変更
        quest.markAsSucceeded(gameDate);

        // 報酬・経験点・名声を付与
        party.forEach(person -> {
            person.getMoney().add(quest.getDifficulty() * 10 / party.size());
            person.getReputation().add(quest.getDifficulty() / 10 / party.size() + 1);
            // 体力消費
            person.getEnergy().consume(1);
        });

        // ギルドに報酬を付与
        gameLogger.important(party.get(0).getName().getFirstName() + "たちが" + quest.getName() + "を完了した！", null, quest);
        gameLogger.debug("TODO 名声と経験点の獲得処理", null, quest);
    }

    private void failure(GameLogger gameLogger) {
        quest.markAsFailed();
        party.forEach(person -> {
            person.getMoney().add(quest.getDifficulty() * 10 / party.size() * -1);
            person.getReputation().add(quest.getDifficulty() / 10 / party.size() * -1);
            person.getEnergy().consume(2);
        });
        gameLogger.fatal(party.get(0).getName().getFirstName() + "たちは" + quest.getName() + "に失敗した…", null, quest);
    }
}
