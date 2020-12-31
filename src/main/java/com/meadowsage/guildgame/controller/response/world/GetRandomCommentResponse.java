package com.meadowsage.guildgame.controller.response.world;

import com.meadowsage.guildgame.usecase.world.GetRandomCommentUseCase;
import lombok.Getter;

@Getter
public class GetRandomCommentResponse {
    String name;
    String message;
    String image;

    public GetRandomCommentResponse(GetRandomCommentUseCase.GetRandomCommentUseCaseResult result) {
        this.name = result.getName();
        this.message = result.getMessage();
        this.image = result.getImage();
    }
}
