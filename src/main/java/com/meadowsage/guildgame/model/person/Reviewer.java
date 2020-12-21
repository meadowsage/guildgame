package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reviewer {

    public List<String> review(Person person) {
        List<String> results = new ArrayList<>();

        if (person.getBattle() <= 25 && person.getKnowledge() <= 25 && person.getSupport() <= 25) {
            results.add("基礎能力がかなり低いようだ。");
        } else if (person.getBattle() > 50 && person.getKnowledge() > 50 && person.getSupport() > 50) {
            results.add("基礎能力が高く、何でもこなせそうだ。");
        } else if(person.getBattle() > 100 ||  person.getKnowledge() > 100 || person.getSupport() > 100) {
            results.add("なぜか威圧感を感じる…");
        }

        if (person.getKnowledge() <= 30 && person.getSupport() <= 30 && person.getBattle() > 60) {
            results.add("良くも悪くも、筋力自慢という印象だ。");
        } else if (person.getBattle() > 80) {
            results.add("身のこなしが普通の人とは違うようだ。");
        } else if (person.getBattle() > 60) {
            results.add("強そうな雰囲気が出ている。");
        }

        if (person.getBattle() <= 30 && person.getSupport() <= 30 && person.getKnowledge() > 60) {
            results.add("頭でっかちな印象を受ける。");
        } else if (person.getKnowledge() > 80) {
            results.add("明晰な頭脳の持ち主だ。");
        } else if (person.getKnowledge() > 60) {
            results.add("頭が良さそうだ。");
        }

        if (person.getBattle() <= 30 && person.getKnowledge() <= 30 && person.getSupport() > 60) {
            results.add("地味だが仕事はできるようだ。");
        } else if (person.getSupport() > 80) {
            results.add("冒険者の心得をよく理解している。");
        } else if (person.getSupport() > 60) {
            results.add("テキパキと手続きをこなしている。");
        }

        if (person.getEnergy().getMax() <= 1) {
            results.add("あまり元気がない。ちゃんと仕事をしてくれるだろうか…");
        } else if (person.getEnergy().getMax() >= 4) {
            results.add("気力に満ちている。精力的に働いてくれそうだ。");
        }

        if (results.size() == 0) {
            results.add("これといった特徴はない。");
        }

        return results;
    }

    public static Reviewer of(Type type) {
        // TODO Interface化して、窓口担当の能力によって異なる評価を下すようにする
        return new Reviewer();
    }

    public enum Type {
        NORMAL
    }
}
