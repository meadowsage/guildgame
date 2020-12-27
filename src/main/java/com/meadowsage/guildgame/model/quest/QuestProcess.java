package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;

import java.util.List;

/**
 * クエストの処理
 */
public class QuestProcess {
    // 目標値 基本・最大・最小
    private static final int TARGET_BASE = 75;
    private static final int TARGET_MAX = 90;
    private static final int TARGET_MIN = 5;

    Quest quest;
    List<Person> party;
    int gameDate;

    public QuestProcess(Quest quest, List<Person> party, int gameDate) {
        this.quest = quest;
        this.party = party;
        this.gameDate = gameDate;
    }

    public void run(Dice dice, GameLogger gameLogger) {
        gameLogger.add("-----" + quest.getName());

        // パーティ全員が成否判定を行い、成功度を合算
        int successPoint = party.stream().mapToInt(person -> roll(person, dice, gameLogger)).sum();

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

        // 行動完了フラグをONにする
        party.forEach(Person::setAsActioned);
    }

    private int roll(Person person, Dice dice, GameLogger gameLogger) {
        // 発揮値：能力値とクエスト種別の係数から算出
        int performance = (int) (person.getBattle().getValue() * quest.getType().getBattleCoefficient() +
                person.getKnowledge().getValue() * quest.getType().getKnowledgeCoefficient() +
                person.getSupport().getValue() * quest.getType().getSupportCoefficient()) / 3;

        System.out.println("- " + person.getName().getFirstName() + ": 発揮値" + performance);

        // 目標値：発揮値との差分で補正
        // 目標値に達していない場合はマイナス補正
        int modifier = (performance - quest.getDifficulty());
        if (modifier < 0) modifier -= 15;
        int target = TARGET_BASE + modifier;
        if (target > TARGET_MAX) target = TARGET_MAX;
        else if (target < TARGET_MIN) target = TARGET_MIN;

        Dice.DiceRollResult result = dice.calcResult(target);
        gameLogger.add("- " + person.getName().getFirstName() + ": [1D100 <= " + target + "] → " +
                result.getNumber() + " " + result.getType().name(), quest);

        // ダイスロールの結果に応じて成功度を決定
        // 失敗した場合は体力を消費
        switch (result.getType()) {
            case CRITICAL:
                return 3;
            case SPECIAL:
                return 2;
            case SUCCESS:
                return 1;
            case FAILURE:
                person.getEnergy().consume(1);
                return 0;
            case FUMBLE:
                person.getEnergy().consume(2);
                return -1;
            default:
                return 0;
        }
    }

    private boolean checkIfCompleted(int successPoint) {
        return successPoint > 0;
    }

    private void success(GameLogger gameLogger) {
        // クエストを完了状態に変更
        quest.complete(gameDate);

        // 報酬・経験点・名声を付与
        party.forEach(person -> {
            person.getMoney().add(quest.getDifficulty() * 10 / party.size());
            person.getReputation().add(quest.getDifficulty() / 10 / party.size() + 1);
            // 体力消費
            person.getEnergy().consume(1);
        });

        // ギルドに報酬を付与

        gameLogger.add(party.get(0).getName().getFirstName() + "たちが" + quest.getName() + "を完了した！", party.get(0), quest);
        gameLogger.add(party.get(0).getName().getFirstName() + "たちは" +
                (quest.getDifficulty() * 10 / party.size()) + "Gと名声" +
                (quest.getDifficulty() / 10 / party.size() + 1) + "を獲得", party.get(0), quest);
    }

    private void failure(GameLogger gameLogger) {
        quest.fail();
        party.forEach(person -> {
            person.getMoney().add(quest.getDifficulty() * 10 / party.size() * -1);
            person.getReputation().add(quest.getDifficulty() / 10 / party.size() * -1);
            person.getEnergy().consume(2);
        });
        gameLogger.add(party.get(0).getName().getFirstName() + "たちは" + quest.getName() + "に失敗した…", party.get(0), quest);
    }
}
