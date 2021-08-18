package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a Bun that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Bun extends Recipes {
    
    /**
     * The required flour for a bun.
     */
    private static final int REQUIRED_FLOUR = 2;
    
    /**
     * The required eggs for a bun.
     */
    private static final int REQUIRED_EGGS = 0;
    
    /**
     * The required milk for a bun.
     */
    private static final int REQUIRED_MILK = 1;
    
    /**
     * The received earning from preparing a bun.
     */
    private static final int EARNINGS = 11;
    
    /**
     * Constructor for a bun. Assigns the values of the required ingredients and earnings.
     */
    public Bun() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}