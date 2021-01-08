package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.mapper.SaveDataMapper;
import com.meadowsage.guildgame.model.system.SaveData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final SaveDataMapper saveDataMapper;

    public SaveData createNewSaveData() {
        SaveData saveData = SaveData.create();
        saveDataMapper.save(saveData);
        return saveData;
    }
}
