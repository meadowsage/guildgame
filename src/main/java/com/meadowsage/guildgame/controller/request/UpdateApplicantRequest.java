package com.meadowsage.guildgame.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateApplicantRequest {
    private long applicantId;
    private boolean isApproval;

    @JsonProperty("isApproval")
    public boolean getIsApproval() {
        return this.isApproval;
    }
}
