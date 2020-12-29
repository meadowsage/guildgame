package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reviewer {

    public List<String> review(Person person) {
        List<String> results = new ArrayList<>();

        int battle = person.getBattle().getValue();
        int knowledge = person.getKnowledge().getValue();
        int support = person.getSupport().getValue();
        int energy = person.getEnergy().getMax();

        if (battle > 100 || knowledge > 100 || support > 100) {
            results.add("不思議な迫力を感じる…");
        } else if (battle > 50 && knowledge > 50 && support > 50) {
            results.add("何でもこなせそうだ。");
        } else if (battle + knowledge + support > 150) {
            results.add("素質がありそうだ。");
        } else if (battle + knowledge + support <= 60) {
            results.add("全体的に能力が低そうだ…");
        }

        if (knowledge <= 25 && support <= 25 && battle > 60) {
            results.add("良くも悪くも筋力自慢という印象だ。");
        } else if (battle > 80) {
            results.add("修羅場をくぐり抜けてきたようだ。");
        } else if (battle > 60) {
            results.add("戦い慣れていそうだ。");
        } else if(battle < 20) {
            results.add("戦闘には向いていないようだ。");
        }

        if (battle <= 25 && support <= 25 && knowledge > 60) {
            results.add("頭でっかちな印象を受ける。");
        } else if (knowledge > 80) {
            results.add("明晰な頭脳の持ち主だ。");
        } else if (knowledge > 60) {
            results.add("頭が良さそうだ。");
        } else if(knowledge < 20) {
            results.add("頭を使う仕事は向いていなさそうだ。");
        }

        if (battle <= 25 && knowledge <= 25 && support > 60) {
            results.add("地味だが仕事はできるようだ。");
        } else if (support > 80) {
            results.add("冒険者の心得をよく理解している。");
        } else if (support > 60) {
            results.add("テキパキと手続きをこなしている。");
        } else if(support < 20) {
            results.add("だいぶ不器用なようだ。");
        }

        if (energy <= 1) {
            results.add("あまり元気がない。");
        } else if (energy >= 4) {
            results.add("気力に満ちている。");
        }

        if (results.size() == 0) {
            results.add("これといった特徴はない。");
        }

        return results;
    }

    public static Reviewer of() {
        // TODO Interface化して、窓口担当の能力によって異なる評価を下すようにする
        return new Reviewer();
    }
}
