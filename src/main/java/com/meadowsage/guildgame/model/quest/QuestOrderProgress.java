package com.meadowsage.guildgame.model.quest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class QuestOrderProgress {
    private int gameDate;
    private int progress;
}
