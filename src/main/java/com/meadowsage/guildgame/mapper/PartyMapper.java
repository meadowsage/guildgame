package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.person.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyMapper {
    void insert(Party party, long worldId);

    void insertPartyMembers(long partyId, List<Long> personIds);

    List<Party> select(Long worldId, Long partyId, Boolean isFree);

    List<Long> selectPartyMemberIds(long id);

    void updatePartyName(long partyId, String partyName);

    void delete(long partyId);

    void deletePartyMember(long partyId, long personId);
}
