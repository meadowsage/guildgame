package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.value.Attribute;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttributeTest {

    @Test
    void test() {
        int n = 1000000;

        int[] results = new int[n];
        for (int i = 0; i < n; i++) {
            results[i] = Attribute.generateRandom().getValue();
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        for (int i = 0; i < 40; i++) {
            int start = i * 5 + 1;
            int end = (i + 1) * 5;
            double ratio = (double) IntStream.of(results).filter(val -> (val >= start && val <= end)).count() / (n / 100);
            System.out.println((start < 10 ? "0" + start : start) + "〜" + (end < 10 ? "0" + end : end) + ": " + IntStream.range(1, (int) (ratio + 1)).mapToObj(val -> "|").collect(Collectors.joining("")) + nf.format(ratio) + "%");
        }
        int limit = 40 * 5 + 1;
        System.out.println(limit + "〜 : " +
                nf.format((double) IntStream.of(results).filter(val -> val > limit).count() / (n / 100)) + "%");

        List<String> rankArr = Arrays.asList("E", "D", "C", "B", "A");

        for (int i = 0; i < 5; i++) {
            int start = i * 20 + 1;
            int end = (i + 1) * 20;
            double ratio = (double) IntStream.of(results).filter(val -> (val >= start && val <= end)).count() / (n / 100);
            System.out.println(rankArr.get(i) + ": " + IntStream.range(1, (int) (ratio + 1)).mapToObj(val -> "|").collect(Collectors.joining("")) + nf.format(ratio) + "%");
        }
        System.out.println("S : " +
                nf.format((double) IntStream.of(results).filter(val -> val > 100).count() / (n / 100)) + "%");


        System.out.println("最大: " + Arrays.stream(results).max().getAsInt());
        System.out.println("平均: " + (int) Arrays.stream(results).average().getAsDouble());
    }

    @Test
    void test2() {
        int n = 100;
        for (int i = 0; i < n; i++) {
            System.out.println(Attribute.generateRandom() + " " + Attribute.generateRandom() + " " + Attribute.generateRandom());
        }
    }

}
