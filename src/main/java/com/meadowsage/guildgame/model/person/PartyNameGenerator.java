package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PartyNameGenerator {

    private final Dice dice;

    public String generate() {
        Dice dice = new Dice();
        return groupA[dice.roll(1, groupA.length) - 1] +
                groupB[dice.roll(1, groupB.length) - 1];
    }

    private static final String[] groupA = new String[]{
            "鷹の",
            "狼の",
            "虎の",
            "幻の",
            "幻影の",
            "真実の",
            "破壊の",
            "恐怖の",
            "自由の",
            "独立の",
            "泥の",
            "大地の",
            "月の",
            "月光の",
            "島の",
            "空の",
            "龍の",
            "金の",
            "銀の",
            "白銀の",
            "白の",
            "拒絶された",
            "神の",
            "町の",
            "村の",
            "世界の",
            "聖なる",
            "鮫の",
            "平和の",
            "空高く",
            "記憶の",
            "昼下がりの",
            "春の",
            "夏の",
            "秋の",
            "冬の",
            "王の",
            "名無しの",
            "歴史の",
            "運命の",
            "鬼の",
            "悪夢の",
            "幸運の",
            "不運の"
    };

    private static final String[] groupB = new String[]{
            "一行",
            "探索者",
            "探求者",
            "集団",
            "旅団",
            "悪巧み",
            "とまり木",
            "休息",
            "剣",
            "鎚",
            "斧",
            "杖",
            "槍",
            "龍",
            "墓",
            "墓守",
            "収穫者",
            "神々",
            "殺戮者",
            "吐息",
            "紳士",
            "捕食者",
            "賊",
            "雪",
            "雨",
            "記憶",
            "両手",
            "言葉",
            "遺言",
            "伝言",
            "噂",
            "朝",
            "夜",
            "夕焼け",
            "昼下がり",
            "守護者",
            "守人",
            "人たち",
            "憤怒",
            "歓喜",
            "反逆者",
            "航海",
            "破壊神",
            "大地",
            "運命",
            "悪夢",
            "根城",
            "吉兆"
    };

}
