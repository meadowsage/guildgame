package com.meadowsage.guildgame.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScenarioMapper {
    void insertCompletedRecord(long worldId, String scenarioId);

    List<String> select(long worldId);
}
