package com.meadowsage.guildgame.usecase.world;

import com.meadowsage.guildgame.model.Guild;
import com.meadowsage.guildgame.model.accounting.*;
import com.meadowsage.guildgame.model.person.Party;
import com.meadowsage.guildgame.model.quest.Quest;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.World;
import com.meadowsage.guildgame.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoNightProcessUseCase {
    private final WorldRepository worldRepository;
    private final GuildRepository guildRepository;
    private final GameLogRepository gameLogRepository;
    private final AccountingRepository accountingRepository;
    private final QuestRepository questRepository;
    private final PartyRepository partyRepository;

    public void run(long worldId) {
        World world = worldRepository.getWorld(worldId);
        if (!world.getState().equals(World.State.NIGHT)) return;

        Guild guild = guildRepository.get(worldId);
        GameLogger gameLogger = new GameLogger(world.getId(), world.getGameDate());
        AccountingLogger accountingLogger = new AccountingLogger(worldId, world.getGameDate());

        // 成功したクエストを抽出
        questRepository.getCompletedQuestOrders(world.getId(), world.getGameDate()).forEach(questOrder -> {
            Quest quest = questRepository.get(questOrder.getQuestId()).orElseThrow(IllegalStateException::new);
            Party party = partyRepository.get(questOrder.getPartyId()).orElseThrow(IllegalStateException::new);

            // クエスト報酬の受け取り（成功時のみ）
            if (questOrder.isSucceeded()) {
                QuestIncome.process(quest, guild, world.getGameDate(), gameLogger, accountingLogger);
            }

            // 冒険者への支払い
            QuestPayment.process(quest, questOrder, party, guild, world.getGameDate(), gameLogger, accountingLogger);

            // 名声の取得
            int gainedReputation = 1 + (quest.getReward().getValue() / 100 + quest.getDanger() * 2) / 10;
            guild.gainReputation(gainedReputation);
            gameLogger.info(quest.getName() + "の達成により名声" + gainedReputation + "を獲得した。");
        });

        // 施設維持費
        FacilityPayment.process("事務所", 250, guild, world.getGameDate(), gameLogger, accountingLogger);

        // 残高を保存
        GuildBalance.record(worldId, guild.getMoney().getValue(), world.getGameDate(), accountingLogger);

        world.changeState(World.State.MIDNIGHT);

        worldRepository.save(world);
        guildRepository.save(guild, worldId);
        gameLogRepository.save(gameLogger);
        accountingRepository.save(accountingLogger);
    }
}
