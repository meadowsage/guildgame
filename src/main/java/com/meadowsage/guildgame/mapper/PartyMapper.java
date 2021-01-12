package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyMapper {
    void insert(Party party, long worldId);

    void insertPartyMembers(long partyId, List<Long> personIds);

    Party select(long worldId);

    List<Party> selectAll(long worldId);

    List<Long> selectPartyMemberIds(long id);

    void delete(long partyId);

    void deletePartyMember(long partyId, long personId);
}
