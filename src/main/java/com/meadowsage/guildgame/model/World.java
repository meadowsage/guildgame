package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.system.SaveData;
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
    private State state;
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
        this.state = State.MORNING;
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

    /**
     * 夜の処理
     * 成長・収支計算など、日中の行動に対する結果を反映する
     */
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

    public void godTime() {
        // 新しい応募者の作成
        addApplicants(Applicant.generate((int) (1 + Math.random() * 2)).stream()
                .map(applicant -> (Person) applicant)
                .collect(Collectors.toList()));
        // 新しいクエストの作成
        addQuests(Quest.generateRandom(3, guild.getReputation()));
    }

    /**
     * 朝の処理
     * リソースの新規生成と、それに対するアクションを行う
     */
    public void morning() {
        // キャラクターを全員未行動状態にする
        getAllPersons().forEach(Person::setAsNotActioned);

        // 冒険者がクエストを受注する
        for (Person person : adventurers) {
            if (!person.isAdventurer() || person.isTired()) continue;
            // TODO 冒険者の名声や好みで重みづけを行い、一番高いものを選択
            quests.stream().filter(quest -> !quest.isReserved())
                    .findFirst().ifPresent(quest -> quest.reserve(person));
        }
    }

    /**
     * 初期リソース生成や設定をここで行う
     * FIXME ID払い出しのためにリポジトリを受け取っている…
     */
    public static World generateAndInit(SaveData saveData, WorldRepository worldRepository) {
        // 初期リソース生成
        World world = new World(
                Guild.create(),
                Collections.singletonList(Person.UniquePerson.TELLAN.getInstance()),
                Collections.singletonList(Quest.UniqueQuest.FIRST.getInstance())
        );

        // 生成したリソースの保存・ID払い出し
        worldRepository.create(world, saveData.getId());

        // クエスト受注を作成
        world.getQuests().get(0).reserve(world.getAdventurers().get(0));

        return world;
    }

    public Optional<Person> findPerson(long personId) {
        Optional<Person> result = adventurers.stream().filter(adventurer -> adventurer.getId() == personId).findAny();
        if (result.isPresent()) return result;
        else return applicants.stream().filter(applicant -> applicant.getId() == personId).findAny();
    }

    public Optional<Quest> getNextQuest(int gameDate) {
        return this.quests.stream().filter(quest -> !quest.hasProcessed(gameDate)).findAny();
    }

    public enum State {
        MORNING, // 朝（ユーザ操作待ち）
        DAYTIME, // 昼（クエスト進行、その他行動）
        NIGHT // 夜（ワールド更新、結果表示）
    }
}
