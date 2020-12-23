package com.meadowsage.guildgame.model.person;

import com.meadowsage.guildgame.model.World;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.value.Attribute;
import com.meadowsage.guildgame.model.value.Money;
import com.meadowsage.guildgame.model.value.Reputation;
import com.meadowsage.guildgame.model.value.Resource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Person {
    @Getter
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

    protected Person(
            PersonName name,
            Attribute battle,
            Attribute knowledge,
            Attribute support,
            Resource energy,
            Money money,
            Reputation reputation
    ) {
        this.name = name;
        this.battle = battle;
        this.knowledge = knowledge;
        this.support = support;
        this.energy = energy;
        this.money = money;
        this.reputation = reputation;
        this.isActioned = false;
    }

    public boolean isNew() {
        return id == -1;
    }

    public boolean isTired() {
        return this.energy.getValue() == 0;
    }

    public abstract boolean isAdventurer();

    public void doDaytimeActivity(World world, GameLogger gameLogger) {
        if (isTired()) {
            // 休息を取る
            gameLogger.add(name.getFirstName() + "は休息をとった。", this);
            energy.recoverToMax();
        } else {
            world.getAvailableQuests().stream().findFirst()
                    .ifPresent(quest -> quest.reserve(this));
        }
    }

    public void setAsActioned() {
        isActioned = true;
    }

    public void setAsNotActioned() {
        isActioned = false;
    }

    private static Person of(
            String firstName,
            String familyName,
            int battle,
            int knowledge,
            int support,
            int energy,
            int money,
            int reputation,
            boolean isAdventurer
    ) {
        if (isAdventurer) {
            return new Adventurer(
                    PersonName.of(firstName, familyName),
                    Attribute.of(battle),
                    Attribute.of(knowledge),
                    Attribute.of(support),
                    Resource.of(energy),
                    Money.of(money),
                    Reputation.of(reputation)
            );
        } else {
            return new Applicant(
                    PersonName.of(firstName, familyName),
                    Attribute.of(battle),
                    Attribute.of(knowledge),
                    Attribute.of(support),
                    Resource.of(energy),
                    Money.of(money),
                    Reputation.of(reputation));
        }

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum UniquePerson {
        TELLAN("テラン", "ウォレス", 28, 41, 62, 1, 2000, 10, true); // Tellan Wallace

        private String firstName;
        private String familyName;
        private int battle;
        private int knowledge;
        private int support;
        private int energy;
        private int money;
        private int reputation;
        private boolean isAdventurer;

        public Person getInstance() {
            return of(firstName, familyName, battle, knowledge, support, energy, money, reputation, isAdventurer);
        }
    }
}
