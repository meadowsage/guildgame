package com.meadowsage.guildgame.mapper;

import com.meadowsage.guildgame.model.accounting.FacilityPayment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityPaymentMapper {
    void insert(FacilityPayment facilityPayment, long worldId);
    List<FacilityPayment> select(long worldId, int gameDate);
}
