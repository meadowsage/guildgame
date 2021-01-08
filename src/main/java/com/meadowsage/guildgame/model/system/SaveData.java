package com.meadowsage.guildgame.model.system;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveData {
    @Getter
    private String id;

    public static SaveData create() {
        SaveData saveData = new SaveData();
        saveData.id = UUID.randomUUID().toString();
        return saveData;
    }
}
