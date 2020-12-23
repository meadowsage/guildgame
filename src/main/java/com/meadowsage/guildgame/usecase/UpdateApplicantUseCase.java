package com.meadowsage.guildgame.usecase;

import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateApplicantUseCase {
    private final PersonRepository personRepository;

    public void run(long applicantId, boolean isApproved) {
        if(isApproved) {
            // 承認 → 応募レコードだけ削除
            personRepository.deleteApplicant(applicantId);
        } else {
            // 拒否 → キャラクターを削除
            personRepository.delete(applicantId);
        }
    }
}
