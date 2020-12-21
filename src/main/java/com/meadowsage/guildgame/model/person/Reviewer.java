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

        if (battle <= 25 && knowledge <= 25 && support <= 25) {
            results.add("基礎能力がかなり低いようだ。");
        } else if (battle > 50 && knowledge > 50 && support > 50) {
            results.add("基礎能力が高く、何でもこなせそうだ。");
        } else if (battle > 100 || knowledge > 100 || support > 100) {
            results.add("なぜか威圧感を感じる…");
        }

        if (knowledge <= 30 && support <= 30 && battle > 60) {
            results.add("良くも悪くも、筋力自慢という印象だ。");
        } else if (battle > 80) {
            results.add("身のこなしが普通の人とは違うようだ。");
        } else if (battle > 60) {
            results.add("強そうな雰囲気が出ている。");
        }

        if (battle <= 30 && support <= 30 && knowledge > 60) {
            results.add("頭でっかちな印象を受ける。");
        } else if (knowledge > 80) {
            results.add("明晰な頭脳の持ち主だ。");
        } else if (knowledge > 60) {
            results.add("頭が良さそうだ。");
        }

        if (battle <= 30 && knowledge <= 30 && support > 60) {
            results.add("地味だが仕事はできるようだ。");
        } else if (support > 80) {
            results.add("冒険者の心得をよく理解している。");
        } else if (support > 60) {
            results.add("テキパキと手続きをこなしている。");
        }

        if (energy <= 1) {
            results.add("あまり元気がない。ちゃんと仕事をしてくれるだろうか…");
        } else if (energy >= 4) {
            results.add("気力に満ちている。精力的に働いてくれそうだ。");
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
