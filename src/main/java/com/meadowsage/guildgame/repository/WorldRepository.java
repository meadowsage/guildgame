package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GuildMapper;
import com.meadowsage.guildgame.mapper.OpenedPlaceMapper;
import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import com.meadowsage.guildgame.mapper.WorldMapper;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.world.World;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class WorldRepository {

    private final WorldMapper worldMapper;
    private final GuildMapper guildMapper;
    private final PersonRepository personRepository;
    private final QuestRepository questRepository;
    private final QuestOrderMapper questOrderMapper;
    private final OpenedPlaceMapper openedPlaceMapper;

    public World getWorld(String saveDataId) {
        return worldMapper.selectBySaveDataId(saveDataId);
    }

    public GameWorld getGameWorld(String saveDataId) {
        World world = worldMapper.selectBySaveDataId(saveDataId);
        return buildGameWorld(world);

    }

    public GameWorld getGameWorld(long worldId) {
        World world = worldMapper.select(worldId);
        return buildGameWorld(world);
    }

    public void saveNew(GameWorld world, String saveDataId) {
        worldMapper.save(world, saveDataId);
        guildMapper.save(world.getGuild(), world.getId());
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));
        world.getQuests().forEach(quest -> questRepository.save(quest, world.getId()));
        world.getPlaces().forEach(place -> openedPlaceMapper.insert(place, world.getId()));
    }

    public void save(GameWorld world) {
        // TODO updated_atで排他制御
        worldMapper.update(world);
        guildMapper.update(world.getGuild(), world.getId());

        // リソース更新
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));
        world.getQuests().forEach(quest -> questRepository.save(quest, world.getId()));

        // クエスト発注レコードの作成・更新
        world.getQuests().stream()
                .map(Quest::getQuestOrders)
                .flatMap(Collection::stream)
                .forEach(questOrder -> {
                    System.out.println(questOrder);
                    if (questOrder.isNew()) questOrderMapper.insert(questOrder);
                    else questOrderMapper.update(questOrder.getId(), questOrder.getState());
                });
    }

    private GameWorld buildGameWorld(World world) {
        return GameWorld.builder()
                .id(world.getId())
                .gameDate(world.getGameDate())
                .state(world.getState())
                .guild(guildMapper.select(world.getId()))
                .adventurers(personRepository.getAdventurers(world.getId()))
                .applicants(personRepository.getApplicants(world.getId()))
                .quests(questRepository.getQuests(world.getId()))
                .places(openedPlaceMapper.select(world.getId()))
                .build();
    }
}
