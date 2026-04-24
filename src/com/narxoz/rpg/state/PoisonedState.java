package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int duration = 3;
    private final int poisonDamage = 5;

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " takes " + poisonDamage + " poison damage!");
        hero.takeDamage(poisonDamage);
        
        if (!hero.isAlive()) {
            System.out.println(hero.getName() + " has died from poison!");
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
        duration--;
        if (duration <= 0) {
            System.out.println(hero.getName() + " has recovered from poison!");
            hero.setState(new NormalState());
        } else {
            System.out.println(hero.getName() + " remains poisoned (" + duration + " turns left)");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}