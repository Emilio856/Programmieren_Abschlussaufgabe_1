package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a Yoghurt that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Yoghurt extends Recipes {
    
    /**
     * The required flour for a yoghurt.
     */
    private static final int REQUIRED_FLOUR = 0;
    
    /**
     * The required eggs for a yoghurt.
     */
    private static final int REQUIRED_EGGS = 0;
    
    /**
     * The required milk for a yoghurt.
     */
    private static final int REQUIRED_MILK = 3;
    
    /**
     * The received earnings from preparing a yoghurt.
     */
    private static final int EARNINGS = 8;
    
    /**
     * Constructor for a yoghurt. Assigns the values of the required ingredients and earnings.
     */
    public Yoghurt() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}