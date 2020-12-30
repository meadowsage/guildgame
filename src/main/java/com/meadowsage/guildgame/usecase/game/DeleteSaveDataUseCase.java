package com.meadowsage.guildgame.usecase.game;

import com.meadowsage.guildgame.mapper.SaveDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteSaveDataUseCase {
    private final SaveDataMapper saveDataMapper;

    public void run(String saveDataId) {
        saveDataMapper.delete(saveDataId);
    }
}
