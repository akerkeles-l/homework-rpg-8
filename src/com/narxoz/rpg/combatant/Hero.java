package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.BerserkState;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public boolean isAlive() { return hp > 0; }
    public HeroState getState() { return state; }
    
    public void setState(HeroState state) {
        System.out.println(name + " state changed from " + this.state.getName() + " to " + state.getName());
        this.state = state;
    }

    public void takeDamage(int amount) {
        int modifiedDamage = state.modifyIncomingDamage(amount);
        int actualDamage = Math.max(1, modifiedDamage - defense);
        hp = Math.max(0, hp - actualDamage);
        System.out.println(name + " takes " + actualDamage + " damage! (HP: " + hp + "/" + maxHp + ")");
        
        if (!(state instanceof BerserkState) && hp > 0 && hp <= maxHp * 0.3) {
            System.out.println(name + " is below 30% HP and enters berserk rage!");
            setState(new BerserkState());
        }
    }

    public void heal(int amount) {
        int oldHp = hp;
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " heals for " + (hp - oldHp) + " HP! (HP: " + hp + "/" + maxHp + ")");
    }
    
    public int attack(Monster monster) {
        if (!state.canAct()) {
            System.out.println(name + " cannot attack due to " + state.getName() + " state!");
            return 0;
        }
        
        int modifiedDamage = state.modifyOutgoingDamage(attackPower);
        int damage = Math.max(1, modifiedDamage);
        monster.takeDamage(damage);
        System.out.println(name + " attacks " + monster.getName() + " for " + damage + " damage!");
        return damage;
    }
    
    public void onTurnStart() {
        state.onTurnStart(this);
    }
    
    public void onTurnEnd() {
        state.onTurnEnd(this);
    }
}