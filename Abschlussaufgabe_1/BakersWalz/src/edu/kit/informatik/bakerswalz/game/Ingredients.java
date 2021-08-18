package edu.kit.informatik.bakerswalz.game;

/**
 * Enum containing all the ingredients and gold for the game and the respective
 * string representations. The enum is able to return these representations of the
 * ingredients for the players.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public enum Ingredients {
    
    /**
     * Gold is used to buy ingredients and win the game.
     */
    GOLD("gold"),
    
    /**
     * Flour is used to prepare recipes.
     */
    FLOUR("flour"),
    
    /**
     * Eggs are used to prepare recipes.
     */
    EGG("egg"),
    
    /**
     * Milk is used to prepare recipes.
     */
    MILK("milk");
    
    /**
     * A string representation of an element.
     */
    private String ingredient;
    
    /**
     * Scheme for the elements in the enum.
     * @param strIngredient The string representation for an element.
     */
    Ingredients(String strIngredient) {
        this.ingredient = strIngredient;
    }
    
    /**
     * Getter for the string representation for an element.
     * @return The ingredients string representation.
     */
    public String getIngredient() {
        return ingredient;
    }
}