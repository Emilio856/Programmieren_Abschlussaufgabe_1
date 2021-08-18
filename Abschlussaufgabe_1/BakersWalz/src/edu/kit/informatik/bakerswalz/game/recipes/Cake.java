package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a cake that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Cake extends Recipes {
    
    /**
     * The required flour for a cake.
     */
    private static final int REQUIRED_FLOUR = 2;
    
    /**
     * The required eggs for a cake.
     */
    private static final int REQUIRED_EGGS = 2;
    
    /**
     * The required milk for a cake.
     */
    private static final int REQUIRED_MILK = 2;
    
    /**
     * The received earning from preparing a cake.
     */
    private static final int EARNINGS = 22;
    
    /**
     * Constructor for a cake. Assigns the values of the required ingredients and earnings.
     */
    public Cake() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}