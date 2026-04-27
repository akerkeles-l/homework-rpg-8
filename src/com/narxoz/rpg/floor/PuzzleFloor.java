package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;

public class PuzzleFloor extends TowerFloor {
    private final String floorName;
    private final String puzzleType;
    private static final Random random = new Random();
    
    public PuzzleFloor(String floorName, String puzzleType) {
        this.floorName = floorName;
        this.puzzleType = puzzleType;
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A " + puzzleType + " puzzle stands before you!");
        System.out.println("Solve it to proceed...");
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int difficulty = random.nextInt(10) + 1;
        System.out.println("Rolling for puzzle solution (need 7+): rolled " + difficulty);
        
        boolean cleared = difficulty >= 7;
        int damageTaken = 0;
        
        if (!cleared) {
            damageTaken = 10;
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.takeDamage(10);
                }
            }
            System.out.println("Failed to solve the puzzle! Party takes 10 damage.");
        } else {
            System.out.println("Successfully solved the puzzle!");
        }
        
        String summary = cleared ? "Solved the " + puzzleType + " puzzle" : "Failed the puzzle";
        return new FloorResult(cleared, damageTaken, summary);
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Treasure found! Party fully healed!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(hero.getMaxHp());
                }
            }
        }
    }
    
    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared();
    }
    
    @Override
    protected void announce() {
        System.out.println("\n❓ ========================================= ❓");
        super.announce();
    }
}