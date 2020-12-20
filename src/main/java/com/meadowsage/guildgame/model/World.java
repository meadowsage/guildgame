package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestProcess;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class World {
    @Getter
    private long id;
    @Getter
    private int gameDate;
    @Getter
    @Setter
    private Guild guild;
    @Getter
    private List<Person> persons = new ArrayList<>();
    @Getter
    private List<Quest> quests = new ArrayList<>();

    public void addPersons(List<Person> persons) {
        this.persons.addAll(persons);
    }

    public void addQuests(List<Quest> quests) {
        this.quests.addAll(quests);
    }

    public void daytime(GameLogger logger) {
        // TODO クエストの実行
        // クエスト＆受注者から、クエストプロセスを生成
        // クエストプロセスを順次実行
        quests.stream().filter(Quest::isReserved).forEach(quest ->
                getPerson(quest.getReservedBy()).ifPresent(person ->
                        new QuestProcess(quest, person).run(new Dice(), logger)
                )
        );
    }

    public void night() {
        // TODO 冒険者の成長
        // 経験点に応じて能力値をアップ
        // 名声に応じてランクをアップ
        // 生活費を差し引く

        // TODO ギルドの更新
        // 維持費を差し引く
        guild.accountingProcess();

        // 新しい応募者冒険者の作成
        addPersons(Person.generateApplicant(3));
        // 新しいクエストの作成
        addQuests(Quest.generateRandom(3, guild.getReputation()));

        // 日付進める
        gameDate++;
    }

    public void morning() {
        // 受注を一度キャンセル
        quests.forEach(Quest::cancel);

        // 冒険者がクエストを受注する
        // TODO 幸運順
        for (Person person : persons) {
            if (!person.isAdventurer()) continue;
            // TODO 冒険者の名声や好みで重みづけを行い、一番高いものを選択
            quests.stream().filter(quest -> !quest.isReserved())
                    .findFirst().ifPresent(quest -> quest.reserve(person));
        }
    }

    public static World create() {
        World world = new World();
        world.id = -1;
        world.gameDate = 1;
        world.guild = Guild.create();
        world.persons = Collections.singletonList(Person.TELLAN);
        world.quests = Collections.singletonList(Quest.FIRST);
        return world;
    }

    public Optional<Person> getPerson(long personId) {
        return persons.stream().filter(person -> person.getId() == personId).findAny();
    }
}
