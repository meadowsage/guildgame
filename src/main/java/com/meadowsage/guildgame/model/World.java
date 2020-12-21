package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.mapper.PersonMapper;
import com.meadowsage.guildgame.model.person.Applicant;
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
import java.util.stream.Stream;

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
    private List<Person> adventurers = new ArrayList<>();
    @Getter
    private List<Person> applicants = new ArrayList<>();
    @Getter
    private List<Quest> quests = new ArrayList<>();

    public World(Guild guild, List<Person> adventurers, List<Quest> quests) {
        this.id = -1;
        this.gameDate = 1;
        this.guild = guild;
        this.adventurers = adventurers;
        this.quests = quests;
    }

    public List<Quest> getAvailableQuests() {
        return quests.stream()
                .filter(quest -> !quest.isReserved())
                .collect(Collectors.toList());
    }

    public List<Person> getAllPersons() {
        return Stream.of(adventurers, applicants).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void addAdventurers(List<Person> adventurers) {
        this.adventurers.addAll(adventurers);
    }

    public void addApplicants(List<Person> applicants) {
        this.applicants.addAll(applicants);
    }

    public void addQuests(List<Quest> quests) {
        this.quests.addAll(quests);
    }

    public void daytime(GameLogger gameLogger) {
        // クエスト処理
        List<QuestProcess> questProcesses = quests.stream().filter(Quest::isReserved).map(quest -> {
            List<Person> persons = quest.getReservedBy().stream()
                    .map(reservedBy -> findPerson(reservedBy).orElse(null))
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

        adventurers.stream().filter(adventurer -> !questPersonIds.contains(adventurer.getId()))
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
        addApplicants(Applicant.generate((int) (1 + Math.random() * 2)).stream()
                .map(applicant -> (Person) applicant)
                .collect(Collectors.toList()));
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
        for (Person person : adventurers) {
            if (!person.isAdventurer() || person.isTired()) continue;
            // TODO 冒険者の名声や好みで重みづけを行い、一番高いものを選択
            quests.stream().filter(quest -> !quest.isReserved())
                    .findFirst().ifPresent(quest -> quest.reserve(person));
        }
    }

    public static World create() {
        return new World(
                Guild.create(),
                Collections.singletonList(Person.UniquePerson.TELLAN.getInstance()),
                Collections.singletonList(Quest.UniqueQuest.FIRST.getInstance())
        );
    }

    public Optional<Person> findPerson(long personId) {
        Optional<Person> result = adventurers.stream().filter(adventurer -> adventurer.getId() == personId).findAny();
        if (result.isPresent()) return result;
        else return applicants.stream().filter(applicant -> applicant.getId() == personId).findAny();
    }

    public void deleteOldResources(PersonMapper personMapper) {
        List<Long> oldApplicantIds = applicants.stream().map(Person::getId).collect(Collectors.toList());
        oldApplicantIds.forEach(personMapper::delete);
        applicants = applicants.stream()
                .filter(person -> oldApplicantIds.contains(person.getId()))
                .collect(Collectors.toList());
    }
}
