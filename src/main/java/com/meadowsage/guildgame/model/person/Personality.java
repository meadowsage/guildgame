package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Personality {
    GENTLE("紳士的"),
    ROUGH("荒くれ者"),
    KIND("親切"),
    COLD("冷たい"),
    POSITIVE("前向き"),
    NEGATIVE("ネガティブ"),
    FRIENDLY("フレンドリー"),
    SHY("内気"),
    QUIET("物静か"),
    CHATTY("おしゃべり"),
    POLITE("礼儀正しい"),
    BRAVE("勇敢"),
    COWARDLY("臆病"),
    AMBITIOUS("野心家"),
    CURIOUS("好奇心旺盛"),
    CRUEL("冷酷"),
    CAREFUL("慎重"),
    CARELESS("ドジ"),
    FAIR("公正"),
    SELFISH("ワガママ"),
    STUDIOUS("勤勉"),
    LAZY("怠惰");

    @Getter
    String label;

    public static Personality getRandom(Dice dice) {
        return Personality.values()[dice.roll(1, Personality.values().length) - 1];
    }

//    /**
//     * 外向性 (<-> 内向性 introversion)
//     * ＜先天的＞高いほど集団行動や新しいことを好み、低いほど単独行動や一つのことへの集中を好む
//     */
//    private var extroversion: Int = 0,
//    /**
//     * 直感（<-> 感覚 sensing）
//     * ＜先天的＞高いほど常識にとらわれない独創的な考えを好み、低いほど歴史や常識に基づく現実的な考えを好む
//     */
//    private var intuition: Int = 0,
//    /**
//     * 思考 (<-> 感情 feeling)
//     * ＜先天的＞高いほど理屈による判断を好み、低いほど感情による判断を好む
//     */
//    private var thinking: Int = 0,
//    /**
//     * 判断的 (<-> 知覚的 perceiving)
//     * ＜先天的＞高いほど規律や計画に従った行動を好み、低いほど柔軟で自由な考え方を好む
//     */
//    private var judging: Int = 0,
//    /**
//     * 自信
//     * ＜後天的＞高いほど判断に迷いがなく頑固で、低いほど優柔不断で従属しやすい
//     */
//    private var confidence: Int = 0,
//    /**
//     * モラル
//     * ＜後天的＞高いほど非道徳的な行為に抵抗を感じ、低いほどためらいなく悪事を働く
//     */
//    private var morality: Int = 0,
//    /**
//     * 礼節
//     * ＜後天的＞高いほど礼節を重んじ、低いほど礼儀作法を気にしない
//     */
//    private var politeness: Int = 0,
//    /**
//     * 思いやり
//     * ＜後天的＞高いほど他者を考慮した行動を好み、低いほど自己中心的
//     */
//    private var thoughtfulness: Int = 0,
//    /**
//     * 猜疑心
//     * ＜後天的＞高いほど他者を疑い、低いほど素直
//     */
//    private var suspicious: Int = 0

//    add(setPersonality(extroversion, "陽気", "明るい", "内気", "一匹狼"))
//    add(setPersonality(intuition, "変人", "独創的", "現実的", "保守的"))
//    add(setPersonality(thinking, "理屈屋", "論理的", "協調的", "感情的"))
//    add(setPersonality(judging, "堅物", "真面目", "柔軟", "奔放"))
//    add(setPersonality(confidence, "自信家", "頼もしい", "優柔不断", "卑屈"))
//    add(setPersonality(morality, "清廉", "善人", "悪人", "非道"))
//    add(setPersonality(politeness, "優雅", "礼儀正しい", "荒っぽい", "無礼"))
//    add(setPersonality(thoughtfulness, "慈悲深い", "優しい", "冷たい", "自己中心的"))
//    add(setPersonality(suspicious, "他者不信", "疑い深い", "素直", "妄信的"))
}
