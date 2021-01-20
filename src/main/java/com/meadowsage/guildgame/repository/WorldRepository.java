package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.OpenedPlaceMapper;
import com.meadowsage.guildgame.mapper.WorldMapper;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Adventurer;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.person.Person;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.model.world.World;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WorldRepository {

    private final WorldMapper worldMapper;
    private final GuildRepository guildRepository;
    private final PersonRepository personRepository;
    private final PartyRepository partyRepository;
    private final QuestRepository questRepository;
    private final OpenedPlaceMapper openedPlaceMapper;

    public World getWorld(String saveDataId) {
        return worldMapper.selectBySaveDataId(saveDataId);
    }

    public World getWorld(long worldId) {
        return worldMapper.select(worldId);
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
        guildRepository.create(world.getGuild(), world.getId());
        world.getAllPersons().forEach(person -> personRepository.save(person, world.getId()));
        // IDが払い出されたメンバで初期パーティを作成
        Party initialParty = Party.createNew("街の世話役", world.getAdventurers());
        world.getParties().add(initialParty);
        partyRepository.save(initialParty, world.getId());
        world.getQuests().forEach(quest -> questRepository.save(quest, world.getId()));
        world.getPlaces().forEach(place -> openedPlaceMapper.insert(place, world.getId()));
    }

    public void save(World world) {
        worldMapper.update(world);
    }

    @Deprecated
    public void save(GameWorld world) {
        // TODO updated_atで排他制御
        worldMapper.update(world);
        guildRepository.save(world.getGuild(), world.getId());

        // 冒険者更新
        // パーティメンバと冒険者リストは重複しているため、別々に更新する
        List<Adventurer> partyMembers = world.getParties().stream()
                .map(Party::getMembers).flatMap(Collection::stream).collect(Collectors.toList());
        List<Long> partyMemberIds = partyMembers.stream().map(Person::getId).collect(Collectors.toList());
        partyMembers.forEach(person -> personRepository.save(person, world.getId()));
        world.getAllPersons().stream().filter(person -> !partyMemberIds.contains(person.getId()))
                .forEach(person -> personRepository.save(person, world.getId()));

        // クエスト更新
        world.getQuests().forEach(quest -> questRepository.save(quest, world.getId()));
        world.getQuestOrders().forEach(questRepository::saveQuestOrder);

        // 追加された場所の解放
        List<Place> opened = openedPlaceMapper.select(world.getId());
        world.getPlaces().stream().filter(place -> !opened.contains(place))
                .forEach(place -> openedPlaceMapper.insert(place, world.getId()));
    }

    private GameWorld buildGameWorld(World world) {
        return GameWorld.builder()
                .id(world.getId())
                .gameDate(world.getGameDate())
                .state(world.getState())
                .guild(guildRepository.get(world.getId()))
                .adventurers(personRepository.getAdventurers(world.getId()))
                .parties(partyRepository.getAll(world.getId()))
                .applicants(personRepository.getApplicants(world.getId()))
                .quests(questRepository.getQuests(world.getId()))
                .questOrders(questRepository.getActiveQuestOrders(world.getId()))
                .places(openedPlaceMapper.select(world.getId()))
                .build();
    }
}
