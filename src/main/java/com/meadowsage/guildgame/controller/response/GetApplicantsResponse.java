package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.ApplicantReviewer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetApplicantsResponse {
    List<ResponseApplicant> applicants;

    public GetApplicantsResponse(List<Applicant> applicants) {
        this.applicants = applicants.stream()
                .map(person -> ResponseApplicant.builder()
                        .id(person.getId())
                        .name(person.getName().getFirstName())
                        .fullName(person.getName().getFullName())
                        .remarks(ApplicantReviewer.of().review(person))
                        .build()
                ).collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class ResponseApplicant {
        long id;
        String name;
        String fullName;
        List<String> remarks;
    }
}
