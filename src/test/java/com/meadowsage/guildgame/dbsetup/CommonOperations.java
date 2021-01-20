package com.meadowsage.guildgame.dbsetup;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.*;

public class CommonOperations {
    private static final String SAVE_DATA_ID = "123456789012345678901234567890123456";

    public static final Operation DELETE_ALL =
            deleteAllFrom("save_data");

    public static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(
                    insertInto("save_data")
                            .columns("id")
                            .values(SAVE_DATA_ID)
                            .build(),
                    insertInto("world")
                            .row()
                            .column("id", 1)
                            .column("save_data_id", SAVE_DATA_ID)
                            .column("game_date", 1)
                            .column("state", "MIDDAY")
                            .end()
                            .build(),
                    insertInto("person")
                            .row()
                            .column("id", 1)
                            .column("world_id", 1)
                            .column("first_name", "first")
                            .column("family_name", "family")
                            .column("money", 100)
                            .column("reputation", 10)
                            .column("battle", 50)
                            .column("battle_exp", 500)
                            .column("knowledge", 50)
                            .column("knowledge_exp", 500)
                            .column("support", 50)
                            .column("support_exp", 500)
                            .column("max_energy", 2)
                            .column("energy", 2)
                            .column("image_body_id", 1)
                            .column("is_actioned", false)
                            .end()
                            .build(),
                    insertInto("party")
                            .row()
                            .column("id", 1)
                            .column("world_id", 1)
                            .column("leader_id", 1)
                            .column("name", "party name")
                            .end()
                            .build(),
                    insertInto("party_member")
                            .row()
                            .column("party_id", 1)
                            .column("person_id", 1)
                            .end()
                            .build()
            );
}
