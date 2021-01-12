package com.meadowsage.guildgame.model.person;

import org.junit.jupiter.api.Test;

public class PersonalityTest {

    @Test
    void test() {
        int n = 100;
        for (int i = 0; i < n; i++) {
            System.out.println(Personality.generateRandom());
        }
    }
}
