package edu.kit.informatik.bakerswalz.game.board;

import edu.kit.informatik.bakerswalz.game.Market;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Interface for a playing field of the board.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public interface PlayingField {
    
    /**
     * The amount of an ingredient a player gets as reward after harvesting.
     */
    int HARVEST_REWARD = 1;
    
    /**
     * The amount of gold a player earns after landing on a certain field.
     * @return The amount of earned gold.
     */
    int earnedGold();
    
    /**
     * Harvests the ingredient from the field a player is located at.
     * @param market The market of the game.
     * @param p The player that will harvest an ingredient.
     */
    void harvestMaterial(Market market, Player p);
    
    /**
     * Checks if the player is allowed to harvest the type of field he is located at.
     * @param market The market of the game.
     * @return True if the player is allowed to harvest.
     */
    boolean canHarvest(Market market);
    
    /**
     * Getter for the type of ingredient a type of field can produce.
     * @return The type of ingredient the field can produce.
     */
    String getIngredient();
}