package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.mapper.GuildMapper;
import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.WorldRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetWorldDataUseCase {
    private final WorldRepository worldRepository;
    private final GuildMapper guildMapper;

    public GetWorldDateUseCaseResult run(String saveDataId) {
        World world = worldRepository.getWorld(saveDataId);
        Guild guild = guildMapper.select(world.getId());
        return new GetWorldDateUseCaseResult(world, guild);
    }

    @Getter
    @AllArgsConstructor
    public static class GetWorldDateUseCaseResult {
        World world;
        Guild guild;
    }
}
