package edu.kit.informatik.bakerswalz.game.recipes;

import edu.kit.informatik.bakerswalz.game.Ingredients;
import edu.kit.informatik.bakerswalz.game.MastersCertificate;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Abstract class for the recipes.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public abstract class Recipes {
    
    /**
     * The String representation for a yoghurt.
     */
    public static final String YOGHURT = "yoghurt";
    
    /**
     * The String representation for a meringue.
     */
    public static final String MERINGUE = "meringue";
    
    /**
     * The String representation for a bread.
     */
    public static final String BREAD = "bread";
    
    /**
     * The String representation for a bun.
     */
    public static final String BUN = "bun";
    
    /**
     * The String representation for a crepe.
     */
    public static final String CREPE = "crepe";
    
    /**
     * The String representation for a pudding.
     */
    public static final String PUDDING = "pudding";
    
    /**
     * The String representation for a cake.
     */
    public static final String CAKE = "cake";
    
    /**
     * Array containing all recipe names.
     */
    public static final String[] RECIPES_ARR = {YOGHURT, MERINGUE, BREAD, BUN, CREPE, PUDDING, CAKE};
    
    /**
     * The required flour for a recipe.
     */
    int requiredFlour;
    
    /**
     * The required eggs for a recipe.
     */
    int requiredEggs;
    
    /**
     * The required milk for a recipe.
     */
    int requiredMilk;
    
    /**
     * The earnings for preparing a recipe.
     */
    int earnings;
    
    /**
     * A player prepares a recipe.
     * @param recipe The recipe prepared by a player.
     * @param p The player that prepares one of the available recipes.
     */
    public final void prepareRecipe(String recipe, Player p) {
        if (checkPlayersMaterials(p)) {
            usePlayersMaterials(p);
            rewardPlayer(p);
            p.getMastersCertificate().baked(recipe);
            
            if (!p.getMastersCertificate().getHasMastersCertificate() && p.getMastersCertificate().hasBakedAll()) {
                p.getMastersCertificate().setHasMastersCertificate(true);
                p.addGold(MastersCertificate.MASTERS_CERTIFICATE_REWARD);
            }
        }
    }
    
    /**
     * Checks if a recipe can be prepared or not.
     * @param str The recipe to be analyzed.
     * @param p The active player.
     * @return True if the player has enough materials to prepare the recipe.
     */
    public final boolean canPrepare(String str, Player p) {
        if (checkPlayersMaterials(p)) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a player has the necessary ingredients to prepare a recipe.
     * @param p The player whose ingredients will be analyzed.
     * @return True if the player has the required materials to prepare a recipe.
     */
    boolean checkPlayersMaterials(Player p) {
        if (p.getFlour() >= requiredFlour && p.getEggs() >= requiredEggs && p.getMilk() >= requiredMilk) {
            return true;
            
        } else {
            return false;
        }
    }
    
    /**
     * Subtracts the materials used for a recipe from the players inventory.
     * @param p The player who prepared a recipe and will loose the used ingredients.
     */
    void usePlayersMaterials(Player p) {
        p.usedMaterial(requiredFlour, Ingredients.FLOUR.getIngredient());
        p.usedMaterial(requiredEggs, Ingredients.EGG.getIngredient());
        p.usedMaterial(requiredMilk, Ingredients.MILK.getIngredient());
    }
    
    /**
     * Rewards a player after preparing a recipe and adds the earned gold to his inventory.
     * @param p The player who will receive the earnings.
     */
    void rewardPlayer(Player p) {
        p.addGold(earnings);
    }
    
    /**
     * Getter for the earnings.
     * @return The earnings from a recipe.
     */
    public int getEarnings() {
        return this.earnings;
    }
}