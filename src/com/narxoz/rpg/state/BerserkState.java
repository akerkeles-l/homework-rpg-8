package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        int modified = (int)(basePower * 1.5);
        System.out.println("Berserk rage increases damage from " + basePower + " to " + modified);
        return modified;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        int modified = (int)(rawDamage * 1.3);
        System.out.println("Berserk recklessness increases incoming damage from " + rawDamage + " to " + modified);
        return modified;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is in a berserk rage!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        if (hero.getHp() > hero.getMaxHp() * 0.3) {
            System.out.println(hero.getName() + " has calmed down from berserk rage");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}