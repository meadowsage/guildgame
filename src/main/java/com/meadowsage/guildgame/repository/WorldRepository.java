package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.*;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorldRepository {

    private final WorldMapper worldMapper;
    private final GuildMapper guildMapper;
    private final PersonMapper personMapper;
    private final ApplicantMapper applicantMapper;
    private final QuestMapper questMapper;
    private final QuestOrderMapper questOrderMapper;

    public World get(String saveDataId) {
        World world = worldMapper.select(saveDataId);
        world.setGuild(guildMapper.select(world.getId()));
        world.addPersons(personMapper.select(world.getId()));
        world.addQuests(questMapper.select(world.getId()));
        return world;
    }

    public void create(World world, String saveDataId) {
        worldMapper.save(world, saveDataId);
        guildMapper.save(world.getGuild(), world.getId());
        world.getPersons().forEach(person -> personMapper.insert(person, world.getId()));
        world.getQuests().forEach(quest -> questMapper.insert(quest, world.getId()));
    }

    public void save(World world) {
        worldMapper.update(world);
        guildMapper.update(world.getGuild(), world.getId());

        // リソース更新
        world.getPersons().stream().filter(person -> !person.isNotSaved()).forEach(personMapper::update);
        world.getQuests().stream().filter(quest -> !quest.isNotSaved()).forEach(questMapper::update);

        // 登録届レコードの作成
        world.getPersons().stream().filter(Person::isApplicant).forEach(applicantMapper::insert);

        // クエスト発注レコードの書き換え
        questOrderMapper.delete(world.getId());
        world.getQuests().stream().filter(Quest::isReserved).forEach(quest ->
                quest.getReservedBy().forEach(reservedBy ->
                        world.getPerson(reservedBy).ifPresent(person -> questOrderMapper.insert(person, quest))));
    }

    public void saveNewDataAndSetIds(World world) {
        world.getPersons().stream().filter(Person::isNotSaved)
                .forEach(person -> personMapper.insert(person, world.getId()));

        world.getQuests().stream().filter(Quest::isNotSaved)
                .forEach(quest -> questMapper.insert(quest, world.getId()));
    }
}
