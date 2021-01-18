package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import org.junit.jupiter.api.Test;

public class PartyNameTest {

    @Test
    void test() {
        int n = 100;
        for (int i = 0; i < n; i++) {
            System.out.println(new PartyNameGenerator(new Dice()).generate());
        }
    }
}
