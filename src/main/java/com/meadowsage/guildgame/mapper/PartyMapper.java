package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyMapper {
    void insert(Party party, long worldId);

    void insertPartyMembers(Party party);

    List<Party> select(long worldId);

    List<Long> selectPartyMemberIds(long id);
}
