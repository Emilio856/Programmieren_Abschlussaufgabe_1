package edu.kit.informatik.bakerswalz.game;

import java.util.HashMap;
import java.util.Map;

import edu.kit.informatik.bakerswalz.game.recipes.Recipes;

/**
 * Models a Masters Certificate. This keeps track of all recipes that have been
 * prepared by the player. Once a player has prepared all recipes at least once,
 * he receives 25 pieces of gold as a reward for completing the certificate.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class MastersCertificate {
    
    /**
     * Reward a player gets once he has prepared all recipes.
     */
    public static final int MASTERS_CERTIFICATE_REWARD = 25;
    
    /**
     * Initial status for all recipes.
     */
    private static final boolean RECIPE_INIT = false;
    
    /**
     * Keeps track of all recipes and marks the ones that have already
     * been prepared by the player.
     */
    private Map<String, Boolean> recipes;
    
    /**
     * Status on the completion of the certificate.
     */
    private boolean hasMastersCertificate;
    
    /**
     * Constructor for MastersCertificate. Initializes the HashMap containing
     * the information about the prepared recipes.
     */
    public MastersCertificate() {
        recipes = new HashMap<>();
        recipes.put(Recipes.YOGHURT, RECIPE_INIT);
        recipes.put(Recipes.MERINGUE, RECIPE_INIT);
        recipes.put(Recipes.BREAD, RECIPE_INIT);
        recipes.put(Recipes.BUN, RECIPE_INIT);
        recipes.put(Recipes.CREPE, RECIPE_INIT);
        recipes.put(Recipes.PUDDING, RECIPE_INIT);
        recipes.put(Recipes.CAKE, RECIPE_INIT);
        
        hasMastersCertificate = false;
    }
    
    /**
     * Checks if all recipes have been prepared.
     * @return True if all recipes have been prepared.
     */
    public boolean hasBakedAll() {
        for (Map.Entry<String, Boolean> entry : recipes.entrySet()) {
            if (entry.getValue()) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    /**
     * Sets the status of a recipe to true meaning that it has now been prepared.
     * @param recipe The recipe that has been prepared.
     */
    public void baked(String recipe) {
        if (!recipes.get(recipe)) {
            recipes.replace(recipe, true);
        }
    }
    
    /**
     * Getter for the status of the players masters certificate.
     * @return The current status of the players masters certificate.
     */
    public boolean getHasMastersCertificate() {
        return this.hasMastersCertificate;
    }
    
    /**
     * Setter for the status of the players masters certificate.
     * @param hasMastersCertificate The new status for the players masters certificate.
     */
    public void setHasMastersCertificate(boolean hasMastersCertificate) {
        this.hasMastersCertificate = hasMastersCertificate;
    }
}