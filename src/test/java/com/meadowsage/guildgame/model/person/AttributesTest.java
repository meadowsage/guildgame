package com.meadowsage.guildgame.model.person;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.stream.IntStream;

public class AttributesTest {

    @Test
    void test() {
        int n = 100000;

        int[] results = new int[n * 3];
        for (int i = 0; i < n; i++) {
            Attributes attributes = Attributes.generateRandom();
            results[i * 3] = attributes.getBattle();
            results[i * 3 + 1] = attributes.getKnowledge();
            results[i * 3 + 2] = attributes.getSupport();
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        System.out.println("0以下: " +
                nf.format((double) IntStream.of(results).filter(val -> val <= 0).count() / (n * 3d / 100)) + "%");
        for (int i = 0; i < 9; i++) {
            int start = i * 10 + 1;
            int end = (i + 1) * 10;
            System.out.println(start + "〜" + end + ": " +
                    nf.format((double) IntStream.of(results).
                            filter(val -> (val >= start && val <= end)).count() / (n * 3d / 100)) + "%");
        }
        System.out.println("101以上: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 100).count() / (n * 3d / 100)) + "%");
    }

}
