package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestOrderResult {
    private int gameDate;
    private boolean succeeded;

    public static QuestOrderResult success(int gameDate) {
        return new QuestOrderResult(gameDate, true);
    }

    public static QuestOrderResult failure(int gameDate) {
        return new QuestOrderResult(gameDate, false);
    }
}
