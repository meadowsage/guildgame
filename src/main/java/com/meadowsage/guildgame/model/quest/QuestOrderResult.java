package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestOrderResult {
    @Getter
    private int gameDate;
    @Getter
    private boolean isSucceeded;

    public static QuestOrderResult success(int gameDate) {
        return new QuestOrderResult(gameDate, true);
    }

    public static QuestOrderResult failure(int gameDate) {
        return new QuestOrderResult(gameDate, false);
    }
}
