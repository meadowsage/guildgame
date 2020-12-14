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
        System.out.println("1〜20: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 0 && val <= 20).count() / (n * 3d / 100)) + "%");
        System.out.println("21〜40: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 20 && val <= 40).count() / (n * 3d / 100)) + "%");
        System.out.println("41〜60: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 40 && val <= 60).count() / (n * 3d / 100)) + "%");
        System.out.println("61〜80: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 60 && val <= 80).count() / (n * 3d / 100)) + "%");
        System.out.println("81〜100: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 80 && val <= 100).count() / (n * 3d / 100)) + "%");
        System.out.println("101以上: " +
                nf.format((double) IntStream.of(results).filter(val -> val > 100).count() / (n * 3d / 100)) + "%");
    }

}
