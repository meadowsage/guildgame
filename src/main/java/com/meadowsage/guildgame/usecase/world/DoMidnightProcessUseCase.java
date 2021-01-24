package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.mapper.OpenedPlaceMapper;
import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.quest.QuestGenerator;
import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.GuildRepository;
import com.meadowsage.guildgame.repository.PersonRepository;
import com.meadowsage.guildgame.repository.QuestRepository;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoMidnightProcessUseCase {
    private final WorldRepository worldRepository;
    private final GuildRepository guildRepository;
    private final PersonRepository personRepository;
    private final QuestRepository questRepository;
    private final OpenedPlaceMapper openedPlaceMapper;

    public void run(long worldId, Dice dice) {
        World world = worldRepository.getGameWorld(worldId);
        if (!world.getState().equals(World.State.MIDNIGHT)) return;

        Guild guild = guildRepository.get(worldId);
        List<Place> openedPlaces = openedPlaceMapper.select(worldId);

        // 新しい応募者の作成
        int numberOfApplicants = personRepository.getApplicants(worldId).size();
        int limitOfNewApplicants = Math.min(guild.getLimitOfApplicants() - numberOfApplicants, 3);
        List<Applicant> applicants = (limitOfNewApplicants > 0)
                ? Applicant.generate(dice.roll(1, limitOfNewApplicants), dice)
                : new ArrayList<>();

        // 新しいクエストの作成
        int questNum = (int) (1 + Math.random() * 2);
        int numberOfQuests = questRepository.getQuests(worldId).size();
        int limitOfNewQuests = Math.min(guild.getLimitOfQuests() - numberOfQuests, 2);
        List<Quest> quests = (limitOfNewQuests > 0)
                ? new QuestGenerator(openedPlaces, dice).generate(dice.roll(1, limitOfNewQuests))
                : new ArrayList<>();

        // 日付を進める
        world.toNextDay();
        world.changeState(World.State.MORNING);

        // 保存
        worldRepository.save(world);
        applicants.forEach(applicant -> personRepository.save(applicant, worldId));
        quests.forEach(quest -> questRepository.save(quest, worldId));
    }
}
