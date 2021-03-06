package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.system.Dice;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Person {
    @Getter
    @Builder.Default
    private long id = -1;
    @Getter
    private PersonName name;
    @Getter
    private Money money;
    @Getter
    private Reputation reputation;
    @Getter
    private Attribute battle;
    @Getter
    private Attribute knowledge;
    @Getter
    private Attribute support;
    @Getter
    private Resource energy;
    @Getter
    private boolean isActioned;
    @Getter
    private List<Personality> personalities = new ArrayList<>();
    @Getter
    private List<PersonSkill> skills = new ArrayList<>();
    @Getter
    private PersonImage personImage;

    public boolean isNew() {
        return id == -1;
    }

    public boolean isTired() {
        return this.energy.getValue() == 0;
    }

    public abstract boolean isAdventurer();

    public abstract void doDaytimeActivity(GameLogger gameLogger);

    public void setAsActioned() {
        isActioned = true;
    }

    public void setAsNotActioned() {
        isActioned = false;
    }

    static Person of(
            String firstName,
            String familyName,
            int battle,
            int knowledge,
            int support,
            int energy,
            int money,
            int reputation,
            List<Personality> personalities,
            List<PersonSkill> skills,
            String imageBody,
            String imageFace,
            String imageEye,
            String imageHair,
            String imageCloth,
            boolean isAdventurer
    ) {
        if (isAdventurer) {
            return Adventurer.builder()
                    .name(PersonName.of(firstName, familyName))
                    .battle(Attribute.of(battle, Attribute.Type.BATTLE))
                    .knowledge(Attribute.of(knowledge, Attribute.Type.KNOWLEDGE))
                    .support(Attribute.of(support, Attribute.Type.SUPPORT))
                    .energy(Resource.of(energy))
                    .money(Money.of(money))
                    .reputation(Reputation.of(reputation))
                    .personalities(personalities)
                    .skills(skills)
                    .personImage(new PersonImage(imageBody, imageFace, imageEye, imageHair, imageCloth))
                    .build();
        } else {
            return Applicant.builder()
                    .name(PersonName.of(firstName, familyName))
                    .battle(Attribute.of(battle, Attribute.Type.BATTLE))
                    .knowledge(Attribute.of(knowledge, Attribute.Type.KNOWLEDGE))
                    .support(Attribute.of(support, Attribute.Type.SUPPORT))
                    .energy(Resource.of(energy))
                    .money(Money.of(money))
                    .reputation(Reputation.of(reputation))
                    .personalities(personalities)
                    .skills(skills)
                    .personImage(new PersonImage(imageBody, imageFace, imageEye, imageHair, imageCloth))
                    .build();
        }
    }

    public String getRandomComment() {
        // TODO 性格や状況で変える
        if (isTired()) return "疲れた……今日は休ませてくれ。";

        String[] comments = new String[]{
                "いいクエストある？",
                "こんにちはー",
                "仕事したくない…",
                "暇だなあ。",
                "支部長さん、調子どう？",
                "ここは平和だねえ。",
                "腕がなまってきたかもしれん。"
        };
        int result = new Dice().roll(1, comments.length);
        return comments[result - 1];
    }
}
