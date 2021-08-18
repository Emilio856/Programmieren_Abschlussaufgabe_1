package edu.kit.informatik.bakerswalz.game.board;

import java.util.Objects;

import edu.kit.informatik.bakerswalz.game.Market;
import edu.kit.informatik.bakerswalz.game.Ingredients;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Models a pasture that can produce milk.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Pasture implements PlayingField {

    /**
     * The gold a player receives for landing on a pasture.
     */
    private static final int PASTURE_GOLD = 0;
    
    /**
     * The ingredient a pasture can produce.
     */
    private static final String INGREDIENT = Ingredients.MILK.getIngredient();
    
    /**
     * Rewards the player with the amount of gold earned when landing on a pasture.
     */
    @Override
    public int earnedGold() {
        return PASTURE_GOLD;
    }
    
    /**
     * Checks if a player is allowed to harvest on a pasture.
     */
    @Override
    public boolean canHarvest(Market market) {
        if (market.canBuy(Ingredients.MILK.getIngredient())) {
            return true;
        }
        return false;
    }

    /**
     * Harvests on a pasture field.
     */
    @Override
    public void harvestMaterial(Market market, Player p) {
        if (canHarvest(market)) {
            market.buyItem(Ingredients.MILK.getIngredient());
            p.addGold(HARVEST_REWARD);
        }
    }
    
    /**
     * Overrides hashCode for a pasture.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Checks if an Object is a pasture.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (!(o instanceof Pasture)) {
            return false;
        }
        
        Pasture p = (Pasture) o;
        
        return Objects.equals(p, o);
    }

    /**
     * Getter for the type of ingredient a pasture can produce.
     */
    @Override
    public String getIngredient() {
        return Pasture.INGREDIENT;
    }
}