package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

public class BossFloor extends TowerFloor {
    private final String floorName;
    private final Monster boss;
    
    public BossFloor(String floorName, Monster boss) {
        this.floorName = floorName;
        this.boss = boss;
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("🔥🔥🔥 BOSS FIGHT! 🔥🔥🔥");
        System.out.println(boss.getName() + " towers before you!");
        System.out.println("Boss stats - HP: " + boss.getHp() + ", Attack: " + boss.getAttackPower());
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamageTaken = 0;
        int turnCount = 0;
        
        while (boss.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            turnCount++;
            System.out.println("\n--- Turn " + turnCount + " ---");
            
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;
                
                hero.onTurnStart();
                if (hero.getState().canAct()) {
                    hero.attack(boss);
                    if (boss.getHp() <= 0) {
                        System.out.println("The boss has been defeated!");
                        break;
                    }
                } else {
                    System.out.println(hero.getName() + " cannot act this turn!");
                }
                hero.onTurnEnd();
            }
            
            if (boss.getHp() <= 0) break;
            
            Hero target = party.stream()
                .filter(Hero::isAlive)
                .findFirst()
                .orElse(null);
                
            if (target != null) {
                int beforeHp = target.getHp();
                boss.attack(target);
                totalDamageTaken += beforeHp - target.getHp();
            }
        }
        
        boolean cleared = party.stream().anyMatch(Hero::isAlive) && boss.getHp() <= 0;
        String summary = cleared ? "Defeated the boss " + boss.getName() : "Failed to defeat the boss";
        
        return new FloorResult(cleared, totalDamageTaken, summary);
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("🏆 LEGENDARY LOOT! 🏆");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(hero.getMaxHp());
                    System.out.println(hero.getName() + " fully restored!");
                }
            }
        }
    }
    
    @Override
    protected void announce() {
        System.out.println("\n👑 ========================================= 👑");
        super.announce();
    }
}