package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.model.person.ApplicantReviewer;
import com.meadowsage.guildgame.model.world.GameWorld;
import com.meadowsage.guildgame.usecase.world.GetWorldDataUseCase;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetWorldResponse {
    ResponseWorld world;
    ResponseGuild guild;
    List<ResponseApplicant> applicants;

    public GetWorldResponse(GetWorldDataUseCase.GetWorldDataUseCaseResult result) {
        GameWorld world = result.getWorld();

        this.world = ResponseWorld.builder()
                .id(world.getId())
                .gameDate(world.getGameDate())
                .state(world.getState().name()).build();

        this.guild = ResponseGuild.builder()
                .money(world.getGuild().getMoney().getValue())
                .reputation(world.getGuild().getReputation()).build();

        this.applicants = world.getApplicants().stream()
                .map(person -> ResponseApplicant.builder()
                        .id(person.getId())
                        .name(person.getName().getFirstName())
                        .fullName(person.getName().getFullName())
                        .remarks(ApplicantReviewer.of().review(person))
                        .build()
                ).collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class ResponseWorld {
        long id;
        int gameDate;
        String state;
    }

    @Builder
    @Getter
    private static class ResponseGuild {
        long money;
        int reputation;
    }

    @Builder
    @Getter
    private static class ResponseApplicant {
        long id;
        String name;
        String fullName;
        List<String> remarks;
    }
}
