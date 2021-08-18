package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a Pudding that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Pudding extends Recipes {
    
    /**
     * The required flour for a pudding.
     */
    private static final int REQUIRED_FLOUR = 0;
    
    /**
     * The required eggs for a pudding.
     */
    private static final int REQUIRED_EGGS = 1;
    
    /**
     * The required milk for a pudding.
     */
    private static final int REQUIRED_MILK = 2;
    
    /**
     * The received earnings from preparing a pudding.
     */
    private static final int EARNINGS = 13;
    
    /**
     * Constructor for a pudding. Assigns the values of the required ingredients and earnings.
     */
    public Pudding() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}