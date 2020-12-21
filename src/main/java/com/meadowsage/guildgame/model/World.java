package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.mapper.PersonMapper;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestProcess;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Quest> getAvailableQuests() {
        return quests.stream()
                .filter(quest -> !quest.isReserved())
                .collect(Collectors.toList());
    }

    public void addPersons(List<Person> persons) {
        this.persons.addAll(persons);
    }

    public void addQuests(List<Quest> quests) {
        this.quests.addAll(quests);
    }

    public void daytime(GameLogger gameLogger) {
        // クエスト処理
        List<QuestProcess> questProcesses = quests.stream().filter(Quest::isReserved).map(quest -> {
            List<Person> persons = quest.getReservedBy().stream()
                    .map(reservedBy -> getPerson(reservedBy).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return new QuestProcess(quest, persons);
        }).collect(Collectors.toList());

        questProcesses.forEach(questProcess -> questProcess.run(new Dice(), gameLogger));

        // クエストに参加しなかったキャラクターの行動
        List<Long> questPersonIds = quests.stream().filter(Quest::isReserved)
                .map(Quest::getReservedBy)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        persons.stream().filter(person -> !questPersonIds.contains(person.getId()))
                .forEach(person -> person.doDaytimeActivity(this, gameLogger));
    }

    public void night(GameLogger gameLogger) {
        // TODO 冒険者の成長
        // 経験点に応じて能力値をアップ
        // 名声に応じてランクをアップ
        // 生活費を差し引く

        // TODO ギルドの更新
        // 維持費を差し引く
        guild.accountingProcess(gameLogger);

        // 日付進める
        gameDate++;
    }

    public void generateNewResources(WorldRepository worldRepository) {
        // 新しい応募者冒険者の作成
        addPersons(Person.generateApplicant((int) (1 + Math.random() * 2)));
        // 新しいクエストの作成
        addQuests(Quest.generateRandom(3, guild.getReputation()));
        // 保存とIDの払い出し
        worldRepository.saveNewResourcesAndGetIds(this);
    }

    public void morning(GameLogger gameLogger) {
        // 受注を一度キャンセル
        quests.forEach(Quest::cancel);

        // 冒険者がクエストを受注する
        // TODO 幸運順
        for (Person person : persons) {
            if (!person.isAdventurer() || person.isTired()) continue;
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
        world.persons = Collections.singletonList(Person.UniquePerson.TELLAN.getInstance());
        world.quests = Collections.singletonList(Quest.UniqueQuest.FIRST.getInstance());
        return world;
    }

    public Optional<Person> getPerson(long personId) {
        return persons.stream().filter(person -> person.getId() == personId).findAny();
    }

    public void deleteOldResources(PersonMapper personMapper) {
        List<Long> oldApplicantIds = persons.stream()
                .filter(person -> !person.isAdventurer())
                .map(Person::getId).collect(Collectors.toList());
        oldApplicantIds.forEach(personMapper::delete);
        persons = persons.stream().filter(person ->
                oldApplicantIds.contains(person.getId())).collect(Collectors.toList());
    }
}
