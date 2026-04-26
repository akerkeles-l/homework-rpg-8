package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

public class CombatFloor extends TowerFloor {
    private final String floorName;
    private Monster monster;
    
    public CombatFloor(String floorName, Monster monster) {
        this.floorName = floorName;
        this.monster = monster;
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A " + monster.getName() + " blocks your path!");
        System.out.println("Monster stats - HP: " + monster.getHp() + ", Attack: " + monster.getAttackPower());
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamageTaken = 0;
        
        while (monster.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;
                
                hero.onTurnStart();
                if (hero.getState().canAct()) {
                    hero.attack(monster);
                }
                hero.onTurnEnd();
                
                if (!monster.isAlive()) break;
            }
            
            if (!monster.isAlive()) break;
            
            Hero target = party.stream().filter(Hero::isAlive).findFirst().orElse(null);
            if (target != null) {
                int beforeHp = target.getHp();
                monster.attack(target);
                totalDamageTaken += beforeHp - target.getHp();
            }
        }
        
        boolean cleared = party.stream().anyMatch(Hero::isAlive) && !monster.isAlive();
        String summary = cleared ? "Defeated " + monster.getName() : "Failed to defeat " + monster.getName();
        
        return new FloorResult(cleared, totalDamageTaken, summary);
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(10);
                }
            }
            System.out.println("Loot: Party healed for 10 HP!");
        }
    }
    
    @Override
    protected void announce() {
        System.out.println("\n⚔️ ========================================= ⚔️");
        super.announce();
    }
}