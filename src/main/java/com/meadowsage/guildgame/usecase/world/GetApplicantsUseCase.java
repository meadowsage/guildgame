package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetApplicantsUseCase {
    private final PersonRepository personRepository;

    public GetApplicantsUseCaseResult run(long worldId) {
        List<Applicant> applicants = personRepository.getApplicants(worldId);
        return new GetApplicantsUseCaseResult(applicants);
    }

    @Data
    @AllArgsConstructor
    public static class GetApplicantsUseCaseResult {
        List<Applicant> applicants;
    }
}
