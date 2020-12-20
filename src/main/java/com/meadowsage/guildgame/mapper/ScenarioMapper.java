package com.meadowsage.guildgame.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScenarioMapper {
    void insertCompletedRecord(long worldId, int scenarioId);

    List<Integer> select(long worldId);
}
