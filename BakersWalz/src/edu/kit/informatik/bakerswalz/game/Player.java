package edu.kit.informatik.bakerswalz.game;

import java.util.HashMap;

import edu.kit.informatik.bakerswalz.ui.Main;

/**
 * Models a player for the Bäckers Walz game. The player can move around the board after rolling
 * the dice. The player can interact with the market when he isn't located on the starting field.
 * He can harvest materials and sell them to the market or buy ingredients from the market.
 * 
 * The ingredients can be used to earn gold preparing recipes. A player wins the game after
 * collecting at least 100 pieces of gold.
 * 
 * A players turn is divided into several parts. First he has to roll the dice and after moving
 * he is able to decide between three options. He can harvest his current field once, buy
 * ingredients from the market several times or directly end his turn.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Player {
    /**
     * Initial amount of gold. Every player has 20 gold at the beginning of the game.
     */
    private static final int INITIAL_GOLD = 20;
    
    /**
     * The amount of gold a player needs to win the game.
     */
    private static final int WINNING_GOLD = 100;
    
    /**
     * Initial amount of flour.
     */
    private static final int INITIAL_FLOUR = 0;
    
    /**
     * Initial amount of eggs.
     */
    private static final int INITIAL_EGG = 0;
    
    /**
     * Initial amount of milk.
     */
    private static final int INITIAL_MILK = 0;
    
    /**
     * Counts the players id. Is incremented after a player is instantiated.
     */
    private static int count = 0;
    
    /**
     * Hash Map to count the amount of gold, flour, eggs, milk a player has.
     */
    private HashMap<String, Integer> materials;
    
    /**
     * Indicates the position of a player in the board.
     */
    private int position;
    
    /**
     * The players masters certificate. It keeps track of all baked recipes
     * and rewards the player when all recipes have been prepared.
     */
    private MastersCertificate mastersCertificate;
    
    /**
     * Defines if the player is allowed to roll the dice or not.
     */
    private boolean canRoll;
    
    /**
     * Defines if the player is allowed to harvest a field or not.
     */
    private boolean canHarvest;
    
    /**
     * Defines if the player is allowed to buy an ingredient or not.
     */
    private boolean canBuy;
    
    /**
     * Defines if the player is allowed to prepare a recipe or not.
     */
    private boolean canPrepare;
    
    /**
     * Defines if the player is able to turn or not.
     */
    private boolean canTurn;
    
    /**
     * The players id.
     */
    private int id;
    
    /**
     * Constructor for a Player. Initializes the Hash map that counts the amount of
     * each material and sets the values to default.
     */
    public Player() {
        mastersCertificate = new MastersCertificate();
        materials = new HashMap<String, Integer>();
        position = 0;
        
        materials.put(Ingredients.GOLD.getIngredient(), INITIAL_GOLD);
        materials.put(Ingredients.FLOUR.getIngredient(), INITIAL_FLOUR);
        materials.put(Ingredients.EGG.getIngredient(), INITIAL_EGG);
        materials.put(Ingredients.MILK.getIngredient(), INITIAL_MILK);
        
        canRoll = true;
        canHarvest = false;
        canBuy = false;
        canPrepare = false;
        canTurn = false;
        
        setId(++count);
    }
    
    /**
     * Subtracts the used materials from the player.
     * @param used The amount used of the material.
     * @param material The material used.
     */
    public void usedMaterial(int used, String material) {
        int before = this.materials.get(material);
        this.materials.replace(material, before - used);
    }
    
    /**
     * Checks if the player has reached the necessary amount of gold to win the game.
     * @return True if the player has won the game.
     */
    public boolean hasWon() {
        return this.getGold() >= WINNING_GOLD;
    }
    
    /**
     * The player buys an ingredient. Increment the amount the player
     * has of the ingredient by one.
     * @param material The bought ingredient.
     */
    public void boughtMaterial(String material) {
        int count = materials.get(material);
        count++;
        this.materials.replace(material, count);
    }
    
    /**
     * Increases the amount of gold a player  by a specific number. 
     * @param increase The amount of gold that is added.
     */
    public void addGold(int increase) {
        int count = materials.get(Ingredients.GOLD.getIngredient());
        this.materials.replace(Ingredients.GOLD.getIngredient(), count + increase);
    }
    
    /**
     * Overrides the toString method for an Object of the type Player.
     * Returns the gold, flour, eggs and milk a player possesses 
     */
    @Override
    public String toString() {
        return materials.get(Ingredients.GOLD.getIngredient()) + Main.SEMICOLON_SEPARATOR
                + materials.get(Ingredients.FLOUR.getIngredient()) + Main.SEMICOLON_SEPARATOR
                + materials.get(Ingredients.EGG.getIngredient()) + Main.SEMICOLON_SEPARATOR
                + materials.get(Ingredients.MILK.getIngredient());
    }
    
    /**
     * Overrides hashCode for a player.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Compares players by id.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (!(o instanceof Player)) {
            return false;
        }
        
        Player p = (Player) o;
        
        return Integer.compare(id, p.id) == 0;
    }
    
    /**
     * Getter for the current amount of gold the player has.
     * @return The amount of gold the player has.
     */
    public int getGold() {
        return this.materials.get(Ingredients.GOLD.getIngredient());
    }
    
    /**
     * Getter for the current amount of flour the player has.
     * @return The amount of flour the player has.
     */
    public int getFlour() {
        return this.materials.get(Ingredients.FLOUR.getIngredient());
    }
    
    /**
     * Getter for  the current amount of eggs the player has.
     * @return The amount of eggs the player has.
     */
    public int getEggs() {
        return this.materials.get(Ingredients.EGG.getIngredient());
    }
    
    /**
     * Getter for the current amount of milk the player has.
     * @return The amount of milk the player has.
     */
    public int getMilk() {
        return this.materials.get(Ingredients.MILK.getIngredient());
    }
    
    /**
     * Getter for the current position of a player.
     * @return The current position of the player.
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Checks if the player is currently allowed to roll the dice.
     * @return True if the player is allowed to roll the dice.
     */
    public boolean getCanRoll() {
        return this.canRoll;
    }
    
    /**
     * Checks if the player is currently allowed to harvest a field.
     * @return True if the player is allowed to harvest a field.
     */
    public boolean getCanHarvest() {
        return this.canHarvest;
    }
    
    /**
     * Checks if the player is currently allowed to buy an ingredient.
     * @return True if the player is allowed to buy an ingredient.
     */
    public boolean getCanBuy() {
        return this.canBuy;
    }
    
    /**
     * Checks if the player is currently allowed to prepare a recipe.
     * @return True if the player is allowed to prepare a recipe.
     */
    public boolean getCanPrepare() {
        return this.canPrepare;
    }
    
    /**
     * Checks if the player is currently allowed to turn.
     * @return True if the player is allowed to turn.
     */
    public boolean getCanTurn() {
        return this.canTurn;
    }
    
    /**
     * Getter for the masters certificate of the player.
     * @return The masters certificate of the player.
     */
    public MastersCertificate getMastersCertificate() {
        return this.mastersCertificate;
    }
    
    /**
     * Getter for the players id.
     * @return The players id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for the players position.
     * @param position The new position of the player.
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Setter for the players permission to roll the dice.
     * @param canRoll New value to allow or deny the player to roll the dice.
     */
    public void setCanRoll(boolean canRoll) {
        this.canRoll = canRoll;
    }
    
    /**
     * Setter for the players permission to harvest a field
     * @param canHarvest New value to allow or deny the player to harvest a field.
     */
    public void setCanHarvest(boolean canHarvest) {
        this.canHarvest = canHarvest;
    }
    
    /**
     * Setter for the players permission to buy an ingredient.
     * @param canBuy New value to allow or deny the player to buy an ingredient.
     */
    public void setCanBuy(boolean canBuy) {
        this.canBuy = canBuy;
    }
    
    /**
     * Setter for the players permission to prepare a recipe.
     * @param canPrepare New value to allow or deny the player to prepare a recipe.
     */
    public void setCanPrepare(boolean canPrepare) {
        this.canPrepare = canPrepare;
    }
    
    /**
     * Setter for the players permission to turn.
     * @param canTurn New value to allow or deny the player to turn.
     */
    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
    }
    
    /**
     * Setter for the players id. Is used to update the players id in the constructor.
     * @param id The new id.
     */
    private void setId(int id) {
        this.id = id;
    }
}