package com.meadowsage.guildgame.model.world;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.accounting.GuildBalance;
import com.meadowsage.guildgame.model.accounting.Treasurer;
import com.meadowsage.guildgame.model.person.*;
import com.meadowsage.guildgame.model.quest.*;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
    private final List<Adventurer> adventurers = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<Applicant> applicants = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<Quest> quests = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<QuestOrder> questOrders = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<Place> places = new ArrayList<>();
    @Getter
    @Builder.Default
    private final List<Party> parties = new ArrayList<>();


    public List<Person> getAllPersons() {
        return Stream.of(adventurers, applicants).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static GameWorld generateAndInit(
            SaveData saveData,
            WorldRepository worldRepository,
            QuestRepository questRepository,
            Treasurer treasurer
    ) {
        // 初期リソース生成
        Adventurer teran = (Adventurer) UniquePerson.TELLAN.getInstance();
        GameWorld world = GameWorld.builder()
                .state(State.MORNING)
                .guild(Guild.create())
                .adventurers(Collections.singletonList(teran))
                .quests(Collections.singletonList(UniqueQuest.FIRST_QUEST.getInstance()))
                .places(Collections.singletonList(Place.CITY))
                .build();

        // 生成したリソースの保存・ID払い出し
        worldRepository.saveNew(world, saveData.getId());

        // クエスト受注を作成
        questRepository.addQuestOrder(world.getQuests().get(0).getId(), world.getParties().get(0).getId());

        // 残高を保存
        treasurer.setGuildBalance(
                new GuildBalance(world.getId(), world.getGuild().getMoney().getValue(), world.gameDate - 1));

        return world;
    }

    public Optional<Adventurer> findAdventurer(long personId) {
        return adventurers.stream()
                .filter(adventurer -> adventurer.getId() == personId).findAny();
    }

    public Optional<QuestOrder> getNextQuestOrder(int gameDate) {
        // 今日まだ実行されていないクエスト受注を抽出し、最もIDが小さいものを取得
        return this.questOrders.stream()
                .filter(questOrder -> !questOrder.hasProcessed(gameDate))
                .min(Comparator.comparing(QuestOrder::getQuestId));
    }

    public void morning() {
        // キャラクターの行動済フラグをリセット
        getAllPersons().forEach(Person::setAsNotActioned);

        adventurers.forEach(adventurer -> adventurer.doMorningActivity(this));

        state = State.MIDDAY;
    }

    public void midday() {
        state = State.AFTERNOON;
    }

    public void afternoon(GameLogger gameLogger) {
        // 未完了のクエストを１個だけ実行
        Optional<QuestOrder> nextQuestOrder = getNextQuestOrder(gameDate);
        if (nextQuestOrder.isPresent()) {
            Quest quest = quests.stream().filter(_quest -> _quest.getId() == nextQuestOrder.get().getQuestId())
                    .findAny().orElseThrow(IllegalStateException::new);
            Party party = parties.stream().filter(_party -> _party.getId() == nextQuestOrder.get().getPartyId())
                    .findAny().orElseThrow(IllegalStateException::new);
            new QuestProcess(quest, nextQuestOrder.get(), party, gameDate).run(new Dice(), gameLogger);
        } else {
            // クエスト処理が全て完了していれば、未行動のキャラクターの行動
            adventurers.stream()
                    .filter(adventurer -> !adventurer.isActioned())
                    .forEach(person -> person.doDaytimeActivity(this, gameLogger));
            state = State.NIGHT;
        }
    }

    public void night(GameLogger gameLogger, Treasurer treasurer) {
        // TODO 冒険者の成長

        // TODO ギルドの更新
//        guild.questAccounting(id, quests, questOrders, adventurers, gameDate, gameLogger, treasurer);

        state = State.MIDNIGHT;
    }

    public void midnight() {
        Dice dice = new Dice();
        // 新しい応募者の作成
        int applicantNum = dice.roll(1, 3);
        applicants.addAll(Applicant.generate(applicantNum, dice));

        // 新しいクエストの作成
        int questNum = (int) (1 + Math.random() * 2);
        quests.addAll(new QuestGenerator(guild.getReputation(), places, new Dice()).generate(questNum));

        // 日付進める
        gameDate++;

        state = State.MORNING;
    }
}
