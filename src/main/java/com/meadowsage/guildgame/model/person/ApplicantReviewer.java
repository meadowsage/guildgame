package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicantReviewer {

    public List<String> review(Person person) {
        List<String> results = new ArrayList<>();

        int battle = person.getBattle().getValue();
        int knowledge = person.getKnowledge().getValue();
        int support = person.getSupport().getValue();
        int energy = person.getEnergy().getMax();

        if (battle > 100 || knowledge > 100 || support > 100) {
            results.add("不思議な迫力を感じる…");
        } else if (battle > 40 && knowledge > 40 && support > 40) {
            results.add("何でもこなせそうなタイプだ。");
        } else if (battle + knowledge + support > 150) {
            results.add("基礎的な能力が高いようだ。");
        } else if (battle + knowledge + support <= 60) {
            results.add("どうにも頼りない印象だ。");
        } else {
            // 戦闘
            if (knowledge <= 25 && support <= 25 && battle > 60) {
                results.add("良くも悪くも筋力自慢という印象だ。");
            } else if (battle > 80) {
                results.add("百戦錬磨の雰囲気を感じる。");
            } else if (battle > 60) {
                results.add("戦い慣れていそうだ。");
            } else if (battle < 20) {
                results.add("戦闘には向いていないようだ。");
            }

            // 知識
            if (battle <= 25 && support <= 25 && knowledge > 60) {
                results.add("頭でっかちな印象を受ける。");
            } else if (knowledge > 80) {
                results.add("とても明晰な頭脳の持ち主だ。");
            } else if (knowledge > 60) {
                results.add("頭が良さそうだ。");
            } else if (knowledge < 20) {
                results.add("頭を使う仕事は向いていなさそうだ。");
            }

            // 支援
            if (battle <= 25 && knowledge <= 25 && support > 60) {
                results.add("地味だが仕事はできそうだ。");
            } else if (support > 80) {
                results.add("冒険者の心得をよく理解している。");
            } else if (support > 60) {
                results.add("テキパキと手続きをこなしている。");
            } else if (support < 20) {
                results.add("気配りが苦手なのかもしれない。");
            }
        }

        if (energy <= 1) {
            results.add("あまり元気がない。");
        } else if (energy >= 4) {
            results.add("とても元気だ。");
        }

        if (results.size() == 0) {
            results.add("これといった特徴はない。");
        }

        return results;
    }

    public static ApplicantReviewer of() {
        // TODO Interface化して、窓口担当の能力によって異なる評価を下すようにする
        return new ApplicantReviewer();
    }
}
