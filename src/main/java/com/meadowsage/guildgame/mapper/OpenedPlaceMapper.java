package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.Place;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpenedPlaceMapper {
    void insert(Place place, long worldId);

    List<Place> select(long worldId);
}
