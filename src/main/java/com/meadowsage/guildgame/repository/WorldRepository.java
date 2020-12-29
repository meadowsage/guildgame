package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GuildMapper;
import com.meadowsage.guildgame.mapper.OpenedPlaceMapper;
import com.meadowsage.guildgame.mapper.QuestOrderMapper;
import com.meadowsage.guildgame.mapper.WorldMapper;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.quest.Quest;
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

    public World get(String saveDataId) {
        World world = worldMapper.select(saveDataId);
        world.setGuild(guildMapper.select(world.getId()));
        world.getAdventurers().addAll(personRepository.getAdventurers(world.getId()));
        world.getApplicants().addAll(personRepository.getApplicants(world.getId()));
        world.getQuests().addAll(questRepository.getOngoingQuests(world.getId()));
        world.getPlaces().addAll(openedPlaceMapper.select(world.getId()));
        return world;
    }

    public void saveNew(World world, String saveDataId) {
        worldMapper.save(world, saveDataId);
        guildMapper.save(world.getGuild(), world.getId());
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));
        world.getQuests().forEach(quest -> questRepository.save(quest, world.getId()));
        world.getPlaces().forEach(place -> openedPlaceMapper.insert(place, world.getId()));
    }

    public void save(World world) {
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
}
