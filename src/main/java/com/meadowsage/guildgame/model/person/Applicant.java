package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends Person {

    @Override
    public boolean isAdventurer() {
        return true;
    }

    @Override
    public void doDaytimeActivity(GameLogger gameLogger) {
        // 何もしない
    }

    public static List<Applicant> generate(int number, Dice dice) {
        return IntStream.range(0, number).mapToObj(value -> {
            PersonName name = PersonName.generateRandom(new PersonNameGenerator());
            Attribute battle = Attribute.generateRandom(Attribute.Type.BATTLE);
            Attribute knowledge = Attribute.generateRandom(Attribute.Type.KNOWLEDGE);
            Attribute support = Attribute.generateRandom(Attribute.Type.SUPPORT);
            Money money = Money.of((int) (500 + Math.random() * 500));
            Reputation reputation = Reputation.of((int) (Math.random() * 10));

            // 戦闘力に応じて体力を調整
            int energyValue = (int) (1 + Math.random() * 4);
            if (battle.getValue() >= 60) energyValue += 1;
            else if (battle.getValue() <= 30) energyValue = Math.max(1, energyValue - 1);
            Resource energy = Resource.of(energyValue);

            // 性格
            List<Personality> personalities = Personality.generateRandom();

            // スキル：ランダムに1〜3個選ぶ
            List<PersonSkill> personSkills = new ArrayList<>();
            for (int i = 0; i < dice.roll(1, 3); i++) {
                PersonSkill nextSkill = PersonSkill.generateRandom(dice);

                if (personSkills.stream().anyMatch(personSkill ->
                        personSkill.getSkill().equals(nextSkill.getSkill()))
                ) continue;

                personSkills.add(nextSkill);
            }

            // 立ち絵
            String imageBody = Integer.toString(dice.roll(1, 1));
            String imageFace = imageBody + "_" + dice.roll(1, 4);
            String imageEye = imageBody + "_" + dice.roll(1, 3);
            String imageHair = imageBody + "_" + dice.roll(1, 3);
            String imageCloth = imageBody + "_" + dice.roll(1, 1);

            return (Applicant) Applicant.builder()
                    .name(name)
                    .battle(battle)
                    .knowledge(knowledge)
                    .support(support)
                    .energy(energy)
                    .money(money)
                    .reputation(reputation)
                    .personalities(personalities)
                    .skills(personSkills)
                    .personImage(new PersonImage(imageBody, imageFace, imageEye, imageHair, imageCloth))
                    .build();
        }).collect(Collectors.toList());
    }
}
