package edu.kit.informatik.bakerswalz.game.board;

import java.util.Objects;
import edu.kit.informatik.bakerswalz.game.Market;
import edu.kit.informatik.bakerswalz.game.Ingredients;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Models a henhouse that can produce eggs.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Henhouse implements PlayingField {

    /**
     * The gold a player receives for landing on a henhouse.
     */
    private static final int HENHOUSE_GOLD = 0;
    
    /**
     * The ingredient a henhouse can produce.
     */
    private static final String INGREDIENT = Ingredients.EGG.getIngredient();
    
    /**
     * Rewards the player with the amount of gold earned when landing on a henhouse.
     */
    @Override
    public int earnedGold() {
        return HENHOUSE_GOLD;
    }

    /**
     * Checks if a player is allowed to harvest on a henhouse.
     */
    @Override
    public boolean canHarvest(Market market) {
        if (market.canBuy(Ingredients.EGG.getIngredient())) {
            return true;
        }
        return false;
    }

    /**
     * Harvests on a henhouse field.
     */
    @Override
    public void harvestMaterial(Market market, Player p) {
        if (canHarvest(market)) {
            market.buyItem(Ingredients.EGG.getIngredient());
            p.addGold(HARVEST_REWARD);
        }
    }
    
    /**
     * Overrides hashCode for a henhouse.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Checks if an Object is a henhouse.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (!(o instanceof Henhouse)) {
            return false;
        }
        
        Henhouse h = (Henhouse) o;
        
        return Objects.equals(h, o);
    }
    
    /**
     * Getter for the type of ingredient a henhouse can produce.
     */
    @Override
    public String getIngredient() {
        return Henhouse.INGREDIENT;
    }
}