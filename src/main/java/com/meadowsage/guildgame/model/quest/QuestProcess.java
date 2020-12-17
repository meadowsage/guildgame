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
        // 達成値の計算
        int performanceSummary = persons.stream().mapToInt(person -> {
            // 目標値80で判定
            // 能力値と難易度の差分で補正（最小5, 最大95）
            int performance = (person.getBattle() + person.getKnowledge() + person.getSupport()) / 3;
            int modifier = (int) ((performance - quest.getDifficulty()) * 1.5);
            if (modifier > 15) modifier = 15;
            else if(modifier < -75) modifier = -75;
            Dice.RollResult rollResult = dice.calcResult(80 + modifier);

            // 判定に成功＝「クエストを達成できるパフォーマンスを発揮できた」とみなす
            switch (rollResult) {
                case CRITICAL:
                    logger.add(person.getName().getFirstName() + "は一撃でモンスターを葬った。", person.getId());
                    return (int) (quest.getDifficulty() * 2.0);
                case SPECIAL:
                    logger.add(person.getName().getFirstName() + "は鮮やかな手さばきで罠を解除した。", person.getId());
                    return (int) (quest.getDifficulty() * 1.5);
                case SUCCESS:
                    return quest.getDifficulty();
                case FAILURE:
                    return (int) (quest.getDifficulty() * 0.5);
                case FUMBLE:
                default:
                    logger.add(person.getName().getFirstName() + "はクエスト中に崖から転落した。", person.getId());
                    return 0;
            }
        }).sum();

        boolean result = performanceSummary >= quest.getDifficulty();

        // 成否に応じてクエストのステータスを更新
        // 成否に応じて冒険者に報酬・経験点・名声を付与
        // 成否に応じてギルドに報酬、名声を付与
        if (result) {
            quest.complete();
            persons.get(0).getMoney().add(100);
            logger.add(persons.get(0).getName().getFirstName() + "たちがクエスト" + quest.getId() + "に成功しました！");
        } else {
            logger.add(persons.get(0).getName().getFirstName() + "たちがクエスト" + quest.getId() + "に失敗しました…");
        }
    }
}
