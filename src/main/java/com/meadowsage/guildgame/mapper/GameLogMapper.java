package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.system.GameLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameLogMapper {
    void insert(GameLog gameLog);

    List<GameLog> selectAll(long worldId, long gameDate);

    List<GameLog> select(long worldId, int gameDate, Long questId, Boolean otherActions);
}
