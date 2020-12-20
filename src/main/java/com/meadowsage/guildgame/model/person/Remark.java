package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Remark {
    @Getter
    private final String value;
    
    public static List<Remark> generateRemarks(Person person) {
        List<Remark> remarks = new ArrayList<>();
        if (person.getBattle() <= 30 && person.getKnowledge() <= 30 && person.getSupport() <= 30) {
            remarks.add(new Remark("基本的な能力がかなり低いようだ。"));
        }

        if (person.getBattle() > 60 && person.getKnowledge() > 60 && person.getSupport() > 60) {
            remarks.add(new Remark("基本的な能力が高い。何でもこなせそうだ。"));
        }

        if (person.getEnergy().getMax() <= 1) {
            remarks.add(new Remark("あまり元気がない。ちゃんと仕事をしてくれるだろうか…"));
        } else if (person.getEnergy().getMax() >= 4) {
            remarks.add(new Remark("気力に満ちている。精力的に働いてくれそうだ。"));
        }

        if (person.getKnowledge() <= 40 && person.getSupport() <= 40 && person.getBattle() > 60) {
            remarks.add(new Remark("良くも悪くも、筋力自慢という印象だ。"));
        } else if (person.getBattle() > 80) {
            remarks.add(new Remark("どんなモンスターにも負けそうにない。"));
        } else if (person.getBattle() > 60) {
            remarks.add(new Remark("戦闘慣れしているようだ。"));
        }

        if (person.getBattle() <= 40 && person.getSupport() <= 40 && person.getKnowledge() > 60) {
            remarks.add(new Remark("良くも悪くも、学者気質という印象だ。"));
        } else if (person.getKnowledge() > 80) {
            remarks.add(new Remark("素晴らしい知恵を持っている。"));
        } else if (person.getKnowledge() > 60) {
            remarks.add(new Remark("頭が良さそうだ。"));
        }

        if (person.getBattle() <= 40 && person.getKnowledge() <= 40 && person.getSupport() > 60) {
            remarks.add(new Remark("地味だが仕事はできるようだ。"));
        } else if (person.getSupport() > 80) {
            remarks.add(new Remark("冒険者の心得をよく理解している。安心して仕事を任せられそうだ。"));
        } else if (person.getSupport() > 60) {
            remarks.add(new Remark("冒険慣れしている。きっと助けになるだろう。"));
        }

        if (remarks.size() == 0) {
            remarks.add(new Remark("特に気になるところはない。"));
        }

        return remarks;
    }
}
