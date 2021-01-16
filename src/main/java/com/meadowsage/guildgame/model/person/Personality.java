package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Personality {
    BRIGHT("明るい", f -> f.extroversion > 70, Collections.emptyList()),
    SHY("内気", f -> f.extroversion < 30, Collections.emptyList()),
    CONFIDENT("自信家", f -> f.confidence > 70, Collections.emptyList()),
    MODEST("謙虚", f -> f.confidence < 30, Collections.emptyList()),
    SINCERE("誠実", f -> f.morality > 70, Collections.emptyList()),
    LIAR("嘘つき", f -> f.morality < 30, Collections.emptyList()),
    GENTLE("紳士的", f -> f.politeness > 70, Collections.emptyList()),
    ROUGH("荒くれ者", f -> f.politeness < 30, Collections.emptyList()),
    KIND("親切", f -> f.thoughtfulness > 70, Collections.emptyList()),
    COLD("冷たい", f -> f.thoughtfulness < 30, Collections.emptyList()),
    WARY("慎重", f -> f.suspicious > 70, Collections.emptyList()),
    DOCILE("素直", f -> f.suspicious < 30, Collections.emptyList()),
    // 明るい+
    LEADERSHIP("リーダー気質", f -> f.extroversion > 70 && f.confidence > 70,
            Arrays.asList(BRIGHT, CONFIDENT)),
    PASSIONATE("熱血", f -> f.extroversion > 70 && f.morality > 70,
            Arrays.asList(BRIGHT, SINCERE)),
    PLEASANT("爽やか", f -> f.extroversion > 70 && f.politeness > 70,
            Arrays.asList(BRIGHT, GENTLE)),
    POWERFUL("豪快", f -> f.extroversion > 70 && f.politeness < 30,
            Arrays.asList(BRIGHT, ROUGH)),
    FRIENDLY("フレンドリー", f -> f.extroversion > 70 && f.thoughtfulness > 70,
            Arrays.asList(BRIGHT, KIND)),
    SIMPLE("単純", f -> f.extroversion > 70 && f.suspicious < 30,
            Arrays.asList(BRIGHT, DOCILE)),
    // 内気+
    SERVILE("卑屈", f -> f.extroversion < 30 && f.confidence < 30,
            Arrays.asList(SHY, MODEST)),
    DILIGENT("真面目", f -> f.extroversion < 30 && f.morality > 70,
            Arrays.asList(SHY, SINCERE)),
    MEAN("卑怯", f -> f.extroversion < 30 && f.morality < 30,
            Arrays.asList(SHY, LIAR)),
    ELEGANT("優雅", f -> f.extroversion < 30 && f.politeness > 70,
            Arrays.asList(SHY, GENTLE)),
    LONER("一匹狼", f -> f.extroversion < 30 && f.thoughtfulness < 30,
            Arrays.asList(SHY, COLD)),
    NERVOUS("神経質", f -> f.extroversion < 30 && f.suspicious > 70,
            Arrays.asList(SHY, WARY)),
    // 自信家+
    SASSY("生意気", f -> f.confidence > 70 && f.politeness < 30,
            Arrays.asList(CONFIDENT, ROUGH)),
    SELFISH("自己中心的", f -> f.confidence > 70 && f.thoughtfulness < 30,
            Arrays.asList(CONFIDENT, COLD)),
    // 卑屈+
    JEALOUS("嫉妬深い", f -> f.confidence < 30 && f.thoughtfulness < 30,
            Arrays.asList(MODEST, COLD)),
    // 嘘つき+
    CRUEL("冷酷", f -> f.morality < 30 && f.thoughtfulness < 30,
            Arrays.asList(LIAR, COLD)),
    SLY("狡猾", f -> f.morality < 30 && f.suspicious > 70,
            Arrays.asList(LIAR, WARY)),
    // 該当なし
    CALM("温厚", f -> false, Collections.emptyList());

    @Getter
    String label;

    Function<PersonalityFactors, Boolean> requirements;

    List<Personality> superiorTo;

    public static List<Personality> generateRandom() {
        Random random = new Random();
        PersonalityFactors personalityFactors = new PersonalityFactors(random);

        List<Personality> results = new ArrayList<>();

        for (Personality personality : Personality.values()) {
            if (personality.requirements.apply(personalityFactors)) {
                results = results.stream().filter(result ->
                        !personality.superiorTo.contains(result)).collect(Collectors.toList());
                results.add(personality);
            }
        }

        // 最大２つ
        if (results.size() > 2) {
            results = results.subList(results.size() - 2, results.size());
        }

        // ０個の場合は「温厚」
        if (results.size() == 0) results.add(Personality.CALM);

        return results;
    }

    @Getter
    private static class PersonalityFactors {
        /*
         * 外向性 (<-> 内向性 introversion)
         * ＜先天的＞高いほど集団行動や新しいことを好み、低いほど単独行動や一つのことへの集中を好む
         */
        int extroversion;
        /*
         * 直感（<-> 感覚 sensing）
         * ＜先天的＞高いほど常識にとらわれない独創的な考えを好み、低いほど歴史や常識に基づく現実的な考えを好む
         */
//        int intuition;
        /*
         * 思考 (<-> 感情 feeling)
         * ＜先天的＞高いほど理屈による判断を好み、低いほど感情による判断を好む
         */
//        int thinking;
        /*
         * 判断的 (<-> 知覚的 perceiving)
         * ＜先天的＞高いほど規律や計画に従った行動を好み、低いほど柔軟で自由な考え方を好む
         */
//        int judging;
        /**
         * 自信
         * ＜後天的＞高いほど判断に迷いがなく頑固で、低いほど優柔不断で従属しやすい
         */
        int confidence;
        /*
         * モラル
         * ＜後天的＞高いほど非道徳的な行為に抵抗を感じ、低いほどためらいなく悪事を働く
         */
        int morality;
        /*
         * 礼節
         * ＜後天的＞高いほど礼節を重んじ、低いほど礼儀作法を気にしない
         */
        int politeness;
        /**
         * 思いやり
         * ＜後天的＞高いほど他者を考慮した行動を好み、低いほど自己中心的
         */
        int thoughtfulness;
        /**
         * 猜疑心
         * ＜後天的＞高いほど他者を疑い、低いほど素直
         */
        int suspicious;

        public PersonalityFactors(Random random) {
            this.extroversion = getRandomGaussian(random);
//            this.intuition = getRandomGaussian(random);
//            this.thinking = getRandomGaussian(random);
//            this.judging = getRandomGaussian(random);
            this.confidence = getRandomGaussian(random);
            this.morality = getRandomGaussian(random);
            this.politeness = getRandomGaussian(random);
            this.thoughtfulness = getRandomGaussian(random);
            this.suspicious = getRandomGaussian(random);
        }

        private static int getRandomGaussian(Random random) {
            return (int) (random.nextGaussian() * 20 + 50);
        }
    }
}
