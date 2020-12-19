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
    public void screening(long applicantId, boolean isApproval) {
        if(isApproval) {
            // 承認 → Adventurerに変更して申請を削除
            personRepository.updateToAdventurer(applicantId);
            personRepository.deleteApplicant(applicantId);
        } else {
            // 拒否 → キャラクターごと削除
            personRepository.delete(applicantId);
        }
    }
}
