package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.*;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("🎮 Welcome to The Haunted Tower RPG! 🎮");
        System.out.println("Demonstrating State Pattern & Template Method Pattern\n");
        
        Hero warrior = new Hero("Thorgrim the Warrior", 100, 25, 15);
        Hero mage = new Hero("Elara the Mage", 70, 35, 8);
        Hero cleric = new Hero("Lucius the Cleric", 85, 18, 12);
        
        System.out.println("=== Hero Creation with Initial States ===");
        System.out.println("Created: " + warrior.getName() + " (Normal state)");
        System.out.println("Created: " + mage.getName() + " (Normal state)");
        System.out.println("Created: " + cleric.getName() + " (Normal state)\n");
        
        List<Hero> heroes = Arrays.asList(warrior, mage, cleric);
        
        Monster goblin = new Monster("Goblin", 45, 12);
        Monster skeleton = new Monster("Skeleton Warrior", 60, 15);
        Monster orc = new Monster("Orc Berserker", 80, 20);
        Monster darkKnight = new Monster("Dark Knight", 100, 25);
        Monster dragon = new Monster("Shadow Dragon", 150, 35);
        
        List<TowerFloor> floors = Arrays.asList(
            new CombatFloor("Goblin Cave", goblin),
            new TrapFloor("Poison Trap Chamber", "poison"),
            new PuzzleFloor("Ancient Riddle Chamber", "riddle"),
            new CombatFloor("Skeleton Crypt", skeleton),
            new TrapFloor("Stunning Gas Chamber", "stun"),
            new CombatFloor("Orc Barracks", orc),
            new PuzzleFloor("Elemental Puzzle", "elemental"),
            new BossFloor("Dark Knight's Throne Room", darkKnight),
            new BossFloor("Dragon's Lair - THE FINAL CHALLENGE", dragon)
        );
        
        TowerRunner towerRunner = new TowerRunner(floors);
        TowerRunResult result = towerRunner.climb(heroes);
        
        System.out.println("\n🎉 ========================================= 🎉");
        System.out.println("🎉 FINAL RESULTS 🎉");
        System.out.println("🎉 ========================================= 🎉");
        System.out.println("✓ Total Floors Cleared: " + result.getFloorsCleared() + " / " + floors.size());
        System.out.println("✓ Heroes Surviving: " + result.getHeroesSurviving() + " / 3");
        System.out.println("✓ Reached the Top: " + (result.isReachedTop() ? "YES - Victory! 🏆" : "NO"));
        
        if (result.isReachedTop()) {
            System.out.println("\n🌟 CONGRATULATIONS! 🌟");
            System.out.println("Your heroes have conquered The Haunted Tower!");
            System.out.println("Legends will be told of their bravery for generations!");
        } else if (result.getHeroesSurviving() > 0) {
            System.out.println("\n💪 Your heroes fought valiantly but couldn't reach the top.");
            System.out.println("Perhaps with more training, they will succeed next time.");
        } else {
            System.out.println("\n💀 The tower remains unconquered...");
            System.out.println("The haunted halls echo with the silence of fallen heroes.");
        }
        
        System.out.println("\n✨ === Pattern Demonstration Summary === ✨");
        System.out.println("🎭 State Pattern:");
        System.out.println("   - Heroes change states: Normal → Poisoned → Normal");
        System.out.println("   - Heroes can enter Berserk state when HP < 30%");
        System.out.println("   - States modify damage and turn behavior");
        System.out.println("   - States self-manage their duration and transitions");
        
        System.out.println("\n📋 Template Method Pattern:");
        System.out.println("   - TowerFloor defines fixed algorithm skeleton");
        System.out.println("   - CombatFloor, TrapFloor, PuzzleFloor, BossFloor implement steps");
        System.out.println("   - Hooks: announce(), shouldAwardLoot(), cleanup()");
        System.out.println("   - Each floor follows same structure but different behavior");
    }
}
