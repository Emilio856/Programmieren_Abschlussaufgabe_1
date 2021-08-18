package edu.kit.informatik.bakerswalz.game.recipes;

/**
 * Modules a crepe that can be prepared by a baker. Contains the required
 * ingredients for its preparation and assigns these values to the recipe
 * in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Crepe extends Recipes {
    
    /**
     * The required flour for a crepe.
     */
    private static final int REQUIRED_FLOUR = 1;
    
    /**
     * The required eggs for a crepe.
     */
    private static final int REQUIRED_EGGS = 2;
    
    /**
     * The required milk for a crepe.
     */
    private static final int REQUIRED_MILK = 0;
    
    /**
     * The received earnings from preparing a crepe.
     */
    private static final int EARNINGS = 12;
    
    /**
     * Constructor for a crepe. Assigns the values of the required ingredients and earnings.
     */
    public Crepe() {
        requiredFlour = REQUIRED_FLOUR;
        requiredEggs = REQUIRED_EGGS;
        requiredMilk = REQUIRED_MILK;
        earnings = EARNINGS;
    }
}