package com.meadowsage.guildgame.model.world;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.accounting.GuildBalance;
import com.meadowsage.guildgame.model.accounting.Treasurer;
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
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuperBuilder
public class GameWorld extends World {
    @Getter
    @Setter
    private Guild guild;
    @Getter
    @Builder.Default
    private List<Adventurer> adventurers = new ArrayList<>();
    @Getter
    @Builder.Default
    private List<Applicant> applicants = new ArrayList<>();
    @Getter
    @Builder.Default
    private List<Quest> quests = new ArrayList<>();
    @Getter
    @Builder.Default
    private List<Place> places = new ArrayList<>();

    public List<Quest> getAvailableQuests() {
        return quests.stream().filter(Quest::isNotOrdered).collect(Collectors.toList());
    }

    public List<Person> getAllPersons() {
        return Stream.of(adventurers, applicants).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static GameWorld generateAndInit(SaveData saveData, WorldRepository worldRepository, Treasurer treasurer) {
        // 初期リソース生成
        GameWorld world = GameWorld.builder()
                .guild(Guild.create())
                .adventurers(Collections.singletonList((Adventurer) UniquePerson.TELLAN.getInstance()))
                .quests(Collections.singletonList(UniqueQuest.FIRST.getInstance()))
                .places(Collections.singletonList(Place.CITY))
                .build();

        // 生成したリソースの保存・ID払い出し
        worldRepository.saveNew(world, saveData.getId());

        // クエスト受注を作成
        world.getQuests().get(0).order(world.getAdventurers().get(0));

        // 残高を保存
        treasurer.setGuildBalance(
                new GuildBalance(world.getId(), world.getGuild().getMoney().getValue(), world.gameDate - 1));

        return world;
    }

    public Optional<Adventurer> findAdventurer(long personId) {
        return adventurers.stream()
                .filter(adventurer -> adventurer.getId() == personId).findAny();
    }

    public Optional<Quest> getNextQuest(int gameDate) {
        return this.quests.stream().filter(quest -> !quest.hasProcessed(gameDate)).findAny();
    }

    public void morning() {
        // キャラクターを全員未行動状態にする
        getAllPersons().forEach(Person::setAsNotActioned);
        adventurers.forEach(adventurer -> adventurer.doMorningActivity(this));

        state = State.MIDDAY;
    }

    public void midday() {
        state = State.AFTERNOON;
    }

    public Optional<Quest> afternoon(GameLogger gameLogger) {
        // 未完了のクエストを１個ずつ実行
        Optional<Quest> nextQuest = getNextQuest(gameDate);
        if (nextQuest.isPresent()) {
            List<Adventurer> party = nextQuest.get().getQuestOrders().stream()
                    .map(questOrder -> findAdventurer(questOrder.getPersonId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            QuestProcess questProcess = new QuestProcess(nextQuest.get(), party, gameDate);
            questProcess.run(new Dice(), gameLogger);
        } else {
            // クエスト処理が完了していれば、未行動のキャラクターの行動
            adventurers.stream()
                    .filter(adventurer -> !adventurer.isActioned())
                    .forEach(person -> person.doDaytimeActivity(this, gameLogger));
            state = State.NIGHT;
        }
        return nextQuest;
    }

    public void night(GameLogger gameLogger, Treasurer treasurer) {
        // TODO 冒険者の成長

        // TODO ギルドの更新
        guild.questAccounting(id, quests, adventurers, gameDate, gameLogger, treasurer);

        state = State.MIDNIGHT;
    }

    public void midnight() {
        // 新しい応募者の作成
        int applicantNum = (int) (1 + Math.random() * 2);
        applicants.addAll(Applicant.generate(applicantNum));

        // 新しいクエストの作成
        int questNum = (int) (1 + Math.random() * 2);
        quests.addAll(new QuestGenerator(guild.getReputation(), places, new Dice()).generate(questNum));

        // 日付進める
        gameDate++;

        state = State.MORNING;
    }
}
