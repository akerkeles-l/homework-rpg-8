package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.FloorResult;
import java.util.List;
import java.util.ArrayList;

public class TowerRunner {
    private final List<TowerFloor> floors;
    
    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }
    
    public TowerRunResult climb(List<Hero> heroes) {
        System.out.println("\n🏰 ========================================= 🏰");
        System.out.println("🏰 THE HAUNTED TOWER CLIMB BEGINS! 🏰");
        System.out.println("🏰 ========================================= 🏰\n");
        
        printPartyStatus(heroes);
        
        int floorsCleared = 0;
        List<Hero> survivingHeroes = new ArrayList<>(heroes);
        
        for (int i = 0; i < floors.size(); i++) {
            TowerFloor floor = floors.get(i);
            System.out.println("\n📍 Floor " + (i + 1) + " of " + floors.size());
            
            FloorResult result = floor.explore(survivingHeroes);
            
            System.out.println("\n📊 Floor " + (i + 1) + " Result: " + result.getSummary());
            System.out.println("   Damage taken: " + result.getDamageTaken());
            System.out.println("   Cleared: " + (result.isCleared() ? "✓ YES" : "✗ NO"));
            
            survivingHeroes.removeIf(hero -> !hero.isAlive());
            
            if (result.isCleared()) {
                floorsCleared++;
                printPartyStatus(survivingHeroes);
            } else {
                System.out.println("\n💀 The party has fallen on floor " + (i + 1) + "! 💀");
                break;
            }
            
            if (survivingHeroes.isEmpty()) {
                System.out.println("\n💀 All heroes have perished! 💀");
                break;
            }
        }
        
        boolean reachedTop = floorsCleared == floors.size() && !survivingHeroes.isEmpty();
        
        System.out.println("\n🏁 ========================================= 🏁");
        System.out.println("🏁 TOWER CLIMB COMPLETE! 🏁");
        System.out.println("🏁 ========================================= 🏁");
        System.out.println("Floors Cleared: " + floorsCleared + "/" + floors.size());
        System.out.println("Heroes Surviving: " + survivingHeroes.size() + "/" + heroes.size());
        System.out.println("Reached the Top: " + (reachedTop ? "✓ YES! 🎉" : "✗ NO"));
        
        if (survivingHeroes.isEmpty()) {
            System.out.println("\nGame Over - No heroes survived!");
        }
        
        return new TowerRunResult(floorsCleared, survivingHeroes.size(), reachedTop);
    }
    
    private void printPartyStatus(List<Hero> heroes) {
        System.out.println("\n✨ Party Status:");
        for (Hero hero : heroes) {
            System.out.printf("  - %s: HP %d/%d | State: %s%n", 
                hero.getName(), hero.getHp(), hero.getMaxHp(), hero.getState().getName());
        }
        System.out.println();
    }
}