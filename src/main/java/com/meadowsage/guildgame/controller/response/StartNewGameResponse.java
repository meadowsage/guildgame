package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.system.SaveData;
import lombok.Data;

@Data
public class StartNewGameResponse {
    String saveDataId;

    public StartNewGameResponse(SaveData saveData) {
        this.saveDataId = saveData.getId();
    }
}
