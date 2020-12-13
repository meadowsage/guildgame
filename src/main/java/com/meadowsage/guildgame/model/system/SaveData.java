package com.meadowsage.guildgame.model.system;

import lombok.Getter;

import java.util.UUID;

public class SaveData {
    @Getter
    private String id;

    public static SaveData create() {
        SaveData saveData = new SaveData();
        saveData.id = UUID.randomUUID().toString();
        return saveData;
    }
}
