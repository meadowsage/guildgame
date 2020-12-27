package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.*;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WorldRepository {

    private final WorldMapper worldMapper;
    private final GuildMapper guildMapper;
    private final PersonRepository personRepository;
    private final ApplicantMapper applicantMapper;
    private final QuestMapper questMapper;
    private final QuestOrderMapper questOrderMapper;
    private final OpenedPlaceMapper openedPlaceMapper;

    public World get(String saveDataId) {
        World world = worldMapper.select(saveDataId);
        world.setGuild(guildMapper.select(world.getId()));
        world.getAdventurers().addAll(personRepository.getAdventurers(world.getId())
                .stream().map(adventurer -> (Person) adventurer).collect(Collectors.toList()));
        world.getApplicants().addAll(personRepository.getApplicants(world.getId())
                .stream().map(applicant -> (Person) applicant).collect(Collectors.toList()));
        world.getQuests().addAll(questMapper.selectAll(world.getId(), QuestOrder.State.ONGOING));
        world.getPlaces().addAll(openedPlaceMapper.select(world.getId()));
        return world;
    }

    public void saveNew(World world, String saveDataId) {
        worldMapper.save(world, saveDataId);
        guildMapper.save(world.getGuild(), world.getId());
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));
        world.getQuests().forEach(quest -> questMapper.insert(quest, world.getId()));
        world.getPlaces().forEach(place -> openedPlaceMapper.insert(place, world.getId()));
    }

    public void save(World world) {
        // TODO updated_atで排他制御
        worldMapper.update(world);
        guildMapper.update(world.getGuild(), world.getId());

        // リソース更新
        world.getQuests().forEach(quest -> {
            if (quest.isNew()) questMapper.insert(quest, world.getId());
            else questMapper.update(quest);
        });
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));

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
