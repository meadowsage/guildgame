package com.meadowsage.guildgame.repository;

import com.meadowsage.guildgame.dbsetup.CommonOperations;
import com.meadowsage.guildgame.model.quest.QuestContent;
import com.meadowsage.guildgame.model.quest.QuestOrder;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class QuestOrderRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private QuestRepository questRepository;

    private static final DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @BeforeEach
    public void prepare() {
        Operation operation =
                sequenceOf(
                        CommonOperations.DELETE_ALL,
                        CommonOperations.INSERT_REFERENCE_DATA,
                        insertInto("quest")
                                .withDefaultValue("world_id", 1)
                                // 完了済
                                .row()
                                .column("id", 1)
                                .column("content", QuestContent.CITY_GUARD)
                                .end()
                                // １回目失敗、２回め進行中
                                .row()
                                .column("id", 2)
                                .column("content", QuestContent.CITY_ASSIST_CONSTRUCTION)
                                .end()
                                // 開始前
                                .row()
                                .column("id", 3)
                                .column("content", QuestContent.CITY_ASSIST_HAUL)
                                .end()
                                // 未受注
                                .row()
                                .column("id", 4)
                                .column("content", QuestContent.M_FOREST_ESCORT_LUMBERJACK)
                                .end()
                                .build(),
                        insertInto("quest_order")
                                // Quest1の受注：完了
                                .row()
                                .column("id", 1)
                                .column("quest_id", 1)
                                .column("party_id", 1)
                                .end()
                                // Quest2 １回めの受注：完了
                                .row()
                                .column("id", 2)
                                .column("quest_id", 2)
                                .column("party_id", 1)
                                .end()
                                // Quest2 ２回めの受注：進行中
                                .row()
                                .column("id", 3)
                                .column("quest_id", 2)
                                .column("party_id", 1)
                                .end()
                                // Quest3の受注：未開始
                                .row()
                                .column("id", 4)
                                .column("quest_id", 3)
                                .column("party_id", 1)
                                .end()
                                .build(),
                        insertInto("quest_order_progress")
                                .row()
                                .column("quest_order_id", 1)
                                .column("game_date", 1)
                                .column("progress", 1)
                                .end()
                                .row()
                                .column("quest_order_id", 2)
                                .column("game_date", 2)
                                .column("progress", 1)
                                .end()
                                .row()
                                .column("quest_order_id", 3)
                                .column("game_date", 3)
                                .column("progress", 1)
                                .end()
                                .build(),
                        insertInto("quest_order_result")
                                .row()
                                .column("quest_order_id", 1)
                                .column("game_date", 1)
                                .column("is_succeeded", true)
                                .end()
                                .row()
                                .column("quest_order_id", 2)
                                .column("game_date", 2)
                                .column("is_succeeded", false)
                                .end()
                                .build()
                );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test
    @DisplayName("getActiveQuestOrders 未完了の受注のみ取得する")
    void test() {
        List<QuestOrder> actual = questRepository.getActiveQuestOrders(1);
        assertThat(actual.size()).isEqualTo(2);
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    @DisplayName("getLastProcessedQuestOrder 最後に実行されたクエスト受注を取得する")
    void test2() {
        questRepository.getLastProcessedQuestOrder(1, 3);
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    @DisplayName("getNextQuestOrder 次に実行すべきクエスト受注を取得する")
    void test3() {
        Optional<QuestOrder> actual = questRepository.getNextQuestOrder(1, 3);
        assertThat(actual.isPresent()).isEqualTo(true);
        actual.ifPresent(questOrder -> {
            assertThat(questOrder.getId()).isEqualTo(4);
        });
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    @DisplayName("getCompletedQuestOrders 完了したクエストを取得する")
    void test4() {
        List<QuestOrder> actual = questRepository.getCompletedQuestOrders(1, 2);
        assertThat(actual.size()).isEqualTo(1);
        Optional.of(actual.get(0)).ifPresent(qo -> {
            assertThat(qo.getId()).isEqualTo(2);
            assertThat(qo.getResult().isPresent()).isEqualTo(true);
            qo.getResult().ifPresent(qor ->
                    assertThat(qor.isSucceeded()).isEqualTo(false));
        });
        dbSetupTracker.skipNextLaunch();
    }
}
