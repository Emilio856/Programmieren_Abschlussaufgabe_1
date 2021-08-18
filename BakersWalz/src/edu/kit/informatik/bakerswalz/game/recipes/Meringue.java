package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a Meringue that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera.
 * @version 1.0
 *
 */
public class Meringue extends Recipes {
    
    /**
     * The required flour for a meringue.
     */
    private static final int REQUIRED_FLOUR = 0;
    
    /**
     * The required eggs for a meringue.
     */
    private static final int REQUIRED_EGGS = 3;
    
    /**
     * The required milk for a meringue.
     */
    private static final int REQUIRED_MILK = 0;
    
    /**
     * The received earnings from preparing a meringue.
     */
    private static final int EARNINGS = 9;
    
    /**
     * Constructor for a meringue. Assigns the values of the required ingredients and earnings.
     */
    public Meringue() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}