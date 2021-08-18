package edu.kit.informatik.bakerswalz.game.board;

import java.util.Objects;
import edu.kit.informatik.bakerswalz.game.Market;
import edu.kit.informatik.bakerswalz.game.Ingredients;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Models a mill that can produce flour.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Mill implements PlayingField {
    
    /**
     * The gold a player receives for landing on a mill.
     */
    private static final int MILL_GOLD = 0;
    
    /**
     * The ingredient a mill can produce.
     */
    private static final String INGREDIENT = Ingredients.FLOUR.getIngredient();

    /**
     * Rewards the player with the amount of gold earned when landing on a mill.
     */
    @Override
    public int earnedGold() {
        return MILL_GOLD;
    }

    /**
     * Checks if a player is allowed to harvest on mill.
     */
    @Override
    public boolean canHarvest(Market market) {
        if (market.canBuy(Ingredients.FLOUR.getIngredient())) {
            return true;
        }
        return false;
    }

    /**
     * Harvests on a mill field.
     */
    @Override
    public void harvestMaterial(Market market, Player p) {
        if (canHarvest(market)) {
            market.buyItem(Ingredients.FLOUR.getIngredient());
            p.addGold(HARVEST_REWARD);
        }
    }
    
    /**
     * Overrides hashCode for a mill.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Checks if an Object is a mill.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (!(o instanceof Mill)) {
            return false;
        }
        
        Mill m = (Mill) o;
        
        return Objects.equals(m, o);
    }

    /**
     * Getter for the type of ingredient a mill can produce.
     */
    @Override
    public String getIngredient() {
        return Mill.INGREDIENT;
    }
}