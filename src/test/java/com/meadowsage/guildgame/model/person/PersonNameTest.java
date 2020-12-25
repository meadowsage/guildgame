package com.meadowsage.guildgame.model.person;

import org.junit.jupiter.api.Test;

public class PersonNameTest {

    @Test
    void test() {
        int n = 100;
        for (int i = 0; i < n; i++) {
            System.out.println(new PersonNameGenerator().generate());
        }
    }
}
