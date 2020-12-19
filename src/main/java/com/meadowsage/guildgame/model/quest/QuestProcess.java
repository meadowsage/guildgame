package com.meadowsage.guildgame.model.quest;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;

import java.util.Collections;
import java.util.List;

/**
 * クエストの実行
 */
public class QuestProcess {
    Quest quest;
    List<Person> persons;

    public QuestProcess(Quest quest, Person person) {
        this.quest = quest;
        this.persons = Collections.singletonList(person);
    }

    public void run(Dice dice, GameLogger logger) {
        logger.add(persons.get(0).getName().getFirstName() + "たちが" +
                quest.getType() + "クエスト（難易度" + quest.getDifficulty() + "）に挑戦", persons.get(0).getId());

        // 参加者ごとに成功度を算出して合算
        int successPoint = persons.stream().mapToInt(person -> {
            // 発揮値：能力値とクエスト種別の係数から算出
            int performance = (int) (person.getBattle() * quest.getType().getBattleCoefficient() +
                    person.getKnowledge() * quest.getType().getKnowledgeCoefficient() +
                    person.getSupport() * quest.getType().getSupportCoefficient()) / 3;

            logger.add(person.getName().getFirstName() + ": 発揮値" + performance);

            // 目標値：基本60、発揮値との差分で補正（最小5, 最大90）
            int target = 60 + (int) ((performance - quest.getDifficulty()) * 1.0);
            if (target > 90) target = 90;
            else if (target < 5) target = 5;

            Dice.DiceRollResult result = dice.calcResult(target);
            logger.add(person.getName().getFirstName() + ": [1D100 <= " + target + "] -> " +
                    result.getNumber() + " " + result.getType().name());

            // ダイスロールの結果に応じて成功度を決定
            switch (result.getType()) {
                case CRITICAL:
                    // TODO クエスト種別や最も高いステータスに応じてメッセージを切り替える
                    logger.add(person.getName().getFirstName() + "は一撃でモンスターを葬った！", person.getId());
                    return 3;
                case SPECIAL:
                    logger.add(person.getName().getFirstName() + "は鮮やかな手さばきで罠を解除した！", person.getId());
                    return 2;
                case SUCCESS:
                    return quest.getDifficulty();
                case FAILURE:
                    return 0;
                case FUMBLE:
                default:
                    logger.add(person.getName().getFirstName() + "はクエスト中に崖から転落した！", person.getId());
                    return -1;
            }
        }).sum();

        // 成否に応じてクエストのステータスを更新
        // 成否に応じて冒険者に報酬・経験点・名声を付与
        // 成否に応じてギルドに報酬、名声を付与
        if (successPoint > 0) {
            quest.complete();
            persons.get(0).getMoney().add(100);
            logger.add(persons.get(0).getName().getFirstName() + "たちがクエストをクリア！", persons.get(0).getId());
        } else {
            logger.add(persons.get(0).getName().getFirstName() + "たちはクエストに失敗…", persons.get(0).getId());
        }
    }
}
