package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.GuildMapper;
import com.meadowsage.guildgame.mapper.PersonMapper;
import com.meadowsage.guildgame.mapper.QuestMapper;
import com.meadowsage.guildgame.mapper.WorldMapper;
import com.meadowsage.guildgame.model.World;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorldRepository {

    private final WorldMapper worldMapper;
    private final GuildMapper guildMapper;
    private final PersonMapper personMapper;
    private final QuestMapper questMapper;

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
        world.getPersons().forEach(person -> personMapper.save(person, world.getId()));
        world.getQuests().forEach(quest -> questMapper.save(quest, world.getId()));
    }

    public void update(World world) {
        worldMapper.update(world);
        guildMapper.update(world.getGuild(), world.getId());
    }
}
