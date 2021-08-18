package edu.kit.informatik.bakerswalz.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.bakerswalz.ui.Main;

/**
 * Models a market. The market can buy harvested ingredients from players and also sell them
 * ingredients. It has a maximum capacity for each ingredient and the selling prices vary based
 * on the current amount in stock. 
 * 
 * The market has a starting amount of two ingredients for each type and can be  by players as 
 * long as they aren't located on a starting field.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Market {
    
    /**
     * Initial amount for each ingredient in the market.
     */
    private static final int INIT_AMOUNT = 2;
    
    /**
     * Lower boundary for the amount of an ingredient in a market.
     * This is also the initial amount for each ingredient.
     */
    private static final int MIN_AMOUNT = 0;
    
    /**
     * Upper boundary for the amount of an ingredient in a market.
     */
    private static final int MAX_AMOUNT = 5;
    
    /**
     * Constant for the formula to determine the prices
     */
    private static final int PRICE_CONSTANT = 6;
    
    /**
     * HashMap to track the eggs, flour and milk in the market.
     */
    private HashMap<String, Integer> market;
    
    /**
     * Constructor for a market. Sets all materials to the initial amount.
     */
    public Market() {
        market = new HashMap<>();
        market.put(Ingredients.FLOUR.getIngredient(), INIT_AMOUNT);
        market.put(Ingredients.EGG.getIngredient(), INIT_AMOUNT);
        market.put(Ingredients.MILK.getIngredient(), INIT_AMOUNT);
    }
    
    /**
     * Checks if the market has storage to buy an element of a certain ingredient.
     * @param ingredient The type of cooking ingredient that the market can or can't
     * buy from a player.
     * @return True if the market has less than 5 elements of this ingredient and
     * therefore is able to buy the an item of this type.
     */
    public boolean canBuy(String ingredient) {
        return validMaterial(ingredient) && market.get(ingredient) < MAX_AMOUNT;
    }
    
    /**
     * Checks if the market is able to sell a given type of ingredient.
     * @param ingredient The type of cooking ingredient that the market can or
     * can't sell to a player.
     * @return True if the market has at least one element of this ingredient and
     * therefore is able to sell the item to a player.
     */
    public boolean canSell(String ingredient) {
        return validMaterial(ingredient) && market.get(ingredient) > MIN_AMOUNT; 
    }
    
    /**
     * Analyzes a String to check if it is a valid ingredient.
     * @param ingredient The String to be analyzed.
     * @return True if the String ingredient is a valid cooking ingredient.
     */
    private boolean validMaterial(String ingredient) {
        
        if (ingredient.equals(Ingredients.FLOUR.getIngredient())
                || ingredient.equals(Ingredients.EGG.getIngredient())
                || ingredient.equals(Ingredients.MILK.getIngredient())) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates the prices based on the currently available amount of items of
     * of a certain type.
     * @param ingredient The ingredient whose price will be calculated.
     * @return The current price for the ingredient.
     */
    public int price(String ingredient) {
        return PRICE_CONSTANT - market.get(ingredient);
    }
    
    /**
     * Sells a cooking ingredient of a given type to a player and
     * updates the market.
     * @param ingredient The ingredient type that is sold to a player.
     */
    public void sellItem(String ingredient) {
        if (canSell(ingredient)) {
            int number = market.get(ingredient);
            number--;
            market.replace(ingredient, number);
        }
    }
    
    /**
     * Buys a cooking ingredient of a given type from a player and
     * updates the market.
     * @param ingredient The ingredient type that is bought from a player.
     */
    public void buyItem(String ingredient) {
        if (canBuy(ingredient)) {
            int number = market.get(ingredient);
            number++;
            market.replace(ingredient, number);
        }
    }
    
    /**
     * Overrides toString() for a market. Returns the sorted amount of each ingredient line by line.
     */
    @Override
    public String toString() {
        Map<String, Integer> sortMap = sortByQuantity(market);
        String materials = "";
        int counter = 1;
        
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            if (counter < 3) {
                materials += entry.getValue() + Main.SEMICOLON_SEPARATOR + entry.getKey() + "\n";
                counter++;
            }
            else {
                materials += entry.getValue() + Main.SEMICOLON_SEPARATOR + entry.getKey();
            }
        }
        return materials;
    }
    
    /**
     * Auxiliary method to sort the markets HashMap into another HashMap for toString().
     * @param map The unsorted HashMap of the market.
     * @return A sorted HashMap of the ingredients.
     */
    private HashMap<String, Integer> sortByQuantity(HashMap<String, Integer> map) {
        
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            
            @Override
            public int compare(Map.Entry<String, Integer> mat1, Map.Entry<String, Integer> mat2) {
                return (mat1.getValue()).compareTo(mat2.getValue());
            }
        });
    
        HashMap<String, Integer> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> temp : list) {
            sorted.put(temp.getKey(), temp.getValue());
        }
        return sorted;
    }
}