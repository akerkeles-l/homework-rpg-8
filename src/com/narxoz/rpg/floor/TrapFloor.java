package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StunnedState;
import java.util.List;

public class TrapFloor extends TowerFloor {
    private final String floorName;
    private final String trapType;
    
    public TrapFloor(String floorName, String trapType) {
        this.floorName = floorName;
        this.trapType = trapType;
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A " + trapType + " trap is triggered!");
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;
        
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            
            switch (trapType) {
                case "poison":
                    hero.setState(new PoisonedState());
                    hero.onTurnStart();
                    damageTaken += 5;
                    break;
                case "stun":
                    hero.setState(new StunnedState());
                    break;
                case "spike":
                    hero.takeDamage(15);
                    damageTaken += 15;
                    break;
                default:
                    hero.takeDamage(10);
                    damageTaken += 10;
            }
        }
        
        boolean cleared = party.stream().anyMatch(Hero::isAlive);
        String summary = "Survived " + trapType + " trap";
        
        return new FloorResult(cleared, damageTaken, summary);
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Found a healing potion in the trap room!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(5);
                }
            }
        }
    }
    
    @Override
    protected void announce() {
        System.out.println("\n⚠️ ========================================= ⚠️");
        super.announce();
    }
}