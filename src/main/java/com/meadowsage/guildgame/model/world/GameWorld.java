package com.meadowsage.guildgame.model.world;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.*;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestGenerator;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.meadowsage.guildgame.model.quest.UniqueQuest;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.SaveData;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuperBuilder
public class GameWorld extends World {
    @Getter
    private final Guild guild;
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
            QuestRepository questRepository
    ) {
        // 初期リソース生成
        Adventurer teran = (Adventurer) UniquePerson.TELLAN.getInstance();
        GameWorld world = GameWorld.builder()
                .gameDate(1)
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

        return world;
    }

    public void midday() {
        state = State.AFTERNOON;
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
