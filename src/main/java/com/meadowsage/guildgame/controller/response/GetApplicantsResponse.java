package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.controller.response.model.ResponsePersonImage;
import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.person.ApplicantReviewer;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetApplicantsResponse {
    List<ResponseApplicant> applicants;

    public GetApplicantsResponse(List<Applicant> applicants) {
        this.applicants = applicants.stream()
                .map(ResponseApplicant::new).collect(Collectors.toList());
    }

    @Getter
    private static class ResponseApplicant {
        long id;
        String name;
        String fullName;
        ResponsePersonImage image;
        List<String> remarks;
        String battle;
        String knowledge;
        String support;

        public ResponseApplicant(Applicant applicant) {
            id = applicant.getId();
            name = applicant.getName().getFirstName();
            fullName = applicant.getName().getFullName();
            image = new ResponsePersonImage(applicant);
            remarks = ApplicantReviewer.of().review(applicant);
            battle = reviewAttribute(applicant.getBattle().getValue());
            knowledge = reviewAttribute(applicant.getKnowledge().getValue());
            support = reviewAttribute(applicant.getSupport().getValue());
        }

        // FIXME
        private String reviewAttribute(int value) {
            int fixed = (value / 2) + (int) (Math.random() * value);
            return fixed < 20 ? "わるい"
                    : fixed > 50 ? "よい"
                    : "ふつう";
        }
    }
}
