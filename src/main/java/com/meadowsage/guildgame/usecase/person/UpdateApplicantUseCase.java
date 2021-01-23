package com.meadowsage.guildgame.usecase.person;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.repository.GuildRepository;
import com.meadowsage.guildgame.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateApplicantUseCase {
    private final GuildRepository guildRepository;
    private final PersonRepository personRepository;

    public void run(long worldId, long applicantId, boolean isApproved) {
        if(isApproved) {
            // 承認 → 応募レコードだけ削除
            // 既に最大数に達している場合はエラー
            Guild guild = guildRepository.get(worldId);
            int numberOfAdventurers = personRepository.getAdventurers(worldId).size();
            if(numberOfAdventurers >= guild.getLimitOfAdventurers()) {
                throw new IllegalStateException();
            }
            personRepository.deleteApplicant(applicantId);
        } else {
            // 拒否 → キャラクターを削除
            personRepository.delete(applicantId);
        }
    }
}
