package edu.kit.informatik.bakerswalz.game;

import java.util.ArrayList;

import edu.kit.informatik.bakerswalz.game.recipes.Recipes;

/**
 * Models a Baker that can prepare Recipes and check what kind of recipes a player can
 * prepare with his current ingredients.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public abstract class Baker {
    
    /**
     * Prepares a specified recipe.
     * @param type The given recipe to prepare.
     * @param p The active player.
     * @return The prepared recipe.
     */
    public Recipes prepareSomething(String type, Player p) {
        Recipes recipe;
        recipe = bakeSomething(type);
        recipe.prepareRecipe(type, p);
        
        return recipe;
    }
    
    /**
     * Analyzes all recipes to check if the active player can prepare them.
     * @param strArr An array with the recipe names.
     * @param p The active player.
     * @return An arraylist with the recipes the active player p can prepare.
     */
    public ArrayList<String> canPrepare(String[] strArr, Player p) {
        ArrayList<String> canPrepare = new ArrayList<>();
        
        for (String str : strArr) {
            Recipes recipe;
            recipe = bakeSomething(str);
            if (recipe.canPrepare(str, p)) {
                canPrepare.add(str);
            }
        }
        return canPrepare;
    }
    
    /**
     * Prepares a given recipe.
     * @param type The recipe to be prepared.
     * @return The prepared recipe.
     */
    abstract Recipes bakeSomething(String type);
}