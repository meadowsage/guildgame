package com.meadowsage.guildgame.model;

import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.person.UniquePerson;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestGenerator;
import com.meadowsage.guildgame.model.quest.QuestProcess;
import com.meadowsage.guildgame.model.quest.UniqueQuest;
import com.meadowsage.guildgame.model.system.Dice;
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
    @Getter
    private List<Place> places = new ArrayList<>();

    private World(
            Guild guild,
            List<Person> adventurers,
            List<Quest> quests,
            List<Place> places
    ) {
        this.id = -1;
        this.gameDate = 1;
        this.state = State.MIDDAY;
        this.guild = guild;
        this.adventurers = adventurers;
        this.quests = quests;
        this.places = places;
    }

    public List<Quest> getAvailableQuests() {
        return quests.stream().filter(Quest::isNotOrdered).collect(Collectors.toList());
    }

    public List<Person> getAllPersons() {
        return Stream.of(adventurers, applicants).flatMap(Collection::stream).collect(Collectors.toList());
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

        state = State.MIDNIGHT;
    }

    public void midnight() {
        // 新しい応募者の作成
        int applicantNum = (int) (Math.random() * 2);
        applicants.addAll(Applicant.generate(applicantNum).stream()
                .map(applicant -> (Person) applicant)
                .collect(Collectors.toList()));

        // 新しいクエストの作成
        int questNum = (int) (1 + Math.random() * 2);
        quests.addAll(new QuestGenerator(
                guild.getReputation(),
                Arrays.stream(Place.values()).collect(Collectors.toList()),
                new Dice()
        ).generate(questNum));

        // 日付進める
        gameDate++;

        state = State.MORNING;
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
            quests.stream().filter(Quest::isNotOrdered).findFirst().ifPresent(quest -> quest.reserve(person));
        }

        state = State.MIDDAY;
    }

    /**
     * 初期リソース生成や設定をここで行う
     * FIXME ID払い出しのためにリポジトリを受け取っている…
     */
    public static World generateAndInit(SaveData saveData, WorldRepository worldRepository) {
        // 初期リソース生成
        World world = new World(
                Guild.create(),
                Collections.singletonList(UniquePerson.TELLAN.getInstance()),
                Collections.singletonList(UniqueQuest.FIRST.getInstance()),
                Collections.singletonList(Place.CITY)
        );

        // 生成したリソースの保存・ID払い出し
        worldRepository.saveNew(world, saveData.getId());

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

    public void afternoon(GameLogger gameLogger) {
        if (state.equals(State.MIDDAY)) state = State.AFTERNOON;

        // 未完了のクエストを１個ずつ実行
        Optional<Quest> nextQuest = getNextQuest(gameDate);
        if (nextQuest.isPresent()) {
            List<Person> party = nextQuest.get().getQuestOrders().stream()
                    .map(questOrder -> findPerson(questOrder.getPersonId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            QuestProcess questProcess = new QuestProcess(nextQuest.get(), party, gameDate);
            questProcess.run(new Dice(), gameLogger);
        } else {
            // クエスト処理が完了していれば、未行動のキャラクターの行動
            adventurers.stream()
                    .filter(person -> person instanceof Adventurer) // 念のためフィルタ
                    .map(person -> (Adventurer) person)
                    .filter(adventurer -> !adventurer.isActioned())
                    .forEach(person -> person.doDaytimeActivity(this, gameLogger));
            state = State.NIGHT;
        }
    }

    public enum State {
        MORNING, // 朝（キャラクター行動決定）
        MIDDAY, // 昼（ユーザ操作待ち）
        AFTERNOON, // 午後（キャラクター行動）
        NIGHT, // 夜（結果表示）
        MIDNIGHT // 深夜（ワールド更新）
    }
}
