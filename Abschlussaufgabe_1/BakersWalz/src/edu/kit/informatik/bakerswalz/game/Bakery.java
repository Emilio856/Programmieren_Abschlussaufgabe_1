package edu.kit.informatik.bakerswalz.game;

import edu.kit.informatik.bakerswalz.game.recipes.Bread;
import edu.kit.informatik.bakerswalz.game.recipes.Bun;
import edu.kit.informatik.bakerswalz.game.recipes.Cake;
import edu.kit.informatik.bakerswalz.game.recipes.Crepe;
import edu.kit.informatik.bakerswalz.game.recipes.Meringue;
import edu.kit.informatik.bakerswalz.game.recipes.Pudding;
import edu.kit.informatik.bakerswalz.game.recipes.Recipes;
import edu.kit.informatik.bakerswalz.game.recipes.Yoghurt;

/**
 * A bakery that can prepare create objects of the recipes depending on the received input.
 * This is latter used to prepare the specified recipe.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Bakery extends Baker {
    
    /**
     * Prepares a given recipe.
     * @param item The recipe to be prepared.
     * @return A new object of the specified recipe or null if the recipe
     * does not exist.
     */
    public Recipes bakeSomething(String item) {
        
        if (item.equals(Recipes.YOGHURT)) {
            return new Yoghurt();
            
        } else if (item.equals(Recipes.MERINGUE)) {
            return new Meringue();
            
        } else if (item.equals(Recipes.BREAD)) {
            return new Bread();
            
        } else if (item.equals(Recipes.BUN)) {
            return new Bun();
            
        } else if (item.equals(Recipes.CREPE)) {
            return new Crepe();
            
        } else if (item.equals(Recipes.PUDDING)) {
            return new Pudding();
            
        } else if (item.equals(Recipes.CAKE)) {
            return new Cake();
            
        } else {
            return null;
        }
    }
}