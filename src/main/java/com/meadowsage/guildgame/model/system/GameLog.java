package com.meadowsage.guildgame.model.system;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameLog {
    @Getter
    Long worldId;
    @Getter
    Long personId;
    @Getter
    Long questId;
    @Getter
    String message;
    @Getter
    int gameDate;
    @Getter
    int logLevel;

    public GameLog(String message, Long worldId, Long personId, Long questId, int gameDate, LogLevel logLevel) {
        this.message = message;
        this.worldId = worldId;
        this.personId = personId;
        this.questId = questId;
        this.gameDate = gameDate;
        this.logLevel = logLevel.value;
    }

    @AllArgsConstructor
    public enum LogLevel {
        FATAL(1), // 緊急（赤字で表示）
        WARNING(2), // 警告（黄色で表示）
        IMPORTANT(3), // 重要（アクセント色で表示）
        INFO(4), // 情報（ホームで見せる情報）
        DETAIL(5), // 詳細（個々の冒険者の実績等）
        DEBUG(6); // デバッグ（ダイスロール等）

        int value;
    }
}
