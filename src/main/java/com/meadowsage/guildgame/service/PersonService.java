package com.meadowsage.guildgame.service;

import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    /**
     * 登録届の審査
     */
    public void updateApplicant(long applicantId, boolean isApproval) {
        if(isApproval) {
            // 承認 → 申請を削除
            personRepository.deleteApplicant(applicantId);
        } else {
            // 拒否 → キャラクターを削除
            personRepository.delete(applicantId);
        }
    }
}
