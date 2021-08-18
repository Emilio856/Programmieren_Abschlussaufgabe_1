package edu.kit.informatik.bakerswalz.game.board;

import java.util.Objects;
import edu.kit.informatik.bakerswalz.game.Market;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Models a starting field.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Start implements PlayingField {

    /**
     * The gold a player receives for landing on a start
     */
    private static final int START_GOLD = 5;
    
    /**
     * Rewards the player with 25 pieces of gold earned when landing on a start.
     */
    @Override
    public int earnedGold() {
        return START_GOLD;
    }
    
    /**
     * A player is not allowed to harvest on a starting field.
     */
    @Override
    public boolean canHarvest(Market market) {
        return false;
    }

    /**
     * A player can't harvest on a starting field.
     */
    @Override
    public void harvestMaterial(Market market, Player p) {
        
    }
    
    /**
     * Overrides hashCode for a starting field.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Checks if an Object is a starting field.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Start)) {
            return false;
        }
        
        Start s = (Start) o;
        
        return Objects.equals(s, o);
    }

    /**
     * Returns null, as a starting field can't produce any ingredient.
     */
    @Override
    public String getIngredient() {
        return null;
    }
}