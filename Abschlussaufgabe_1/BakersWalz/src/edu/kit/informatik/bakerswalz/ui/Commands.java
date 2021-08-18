package edu.kit.informatik.bakerswalz.ui;

import java.util.ArrayList;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.bakerswalz.game.Baker;
import edu.kit.informatik.bakerswalz.game.Bakery;
import edu.kit.informatik.bakerswalz.game.GameBoard;
import edu.kit.informatik.bakerswalz.game.BackersWalzUtility;
import edu.kit.informatik.bakerswalz.game.Ingredients;
import edu.kit.informatik.bakerswalz.game.Player;
import edu.kit.informatik.bakerswalz.game.recipes.Recipes;

/**
 * Contains the commands that can be executed by the player.
 * 
 * A player can roll the dice with on a wished number, harvest the field he
 * is located at and buy ingredients from the market. He can also get a list
 * of the recipes he can prepare with his ingredients and prepare these recipes
 * to earn gold. 
 * 
 * He can also get a sorted list of all ingredients on the market and see the
 * resources other players possess. The player can decide when to end his turn
 * and quit the game at any moment.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Commands {
    
    /**
     * The GameBoard for the game.
     */
    private static GameBoard myBoard;
    
    /**
     * Array of the type Player with the participating players.
     */
    private static Player[] players;
    
    /**
     * Index that compensates for the difference to array indices.
     */
    private static final int PLAYER_DIFF = 1;
    
    /**
     * Index that compensates for the difference to array indices and adds one to get next player.
     */
    private static final int NEXT_PLAYER_DIFF = 2;
    
    /**
     * Index for the first player
     */
    private static final int PLAYER_ONE = 1;
    
    /**
     * Defines the status of the game. Game is finished when quit == true;
     */
    private boolean quit;
    
    /**
     * Defines if the game was has been won or not.
     */
    private boolean gameWon;
    
    /**
     * Constructor for Commands. Contains all commands the players can execute in the game.
     * @param myBoard The board in the game.
     * @param players The players of the game.
     */
    public Commands(GameBoard myBoard, Player[] players) {
        Commands.myBoard = myBoard;
        Commands.players = players;
        quit = false;
        gameWon = false;
    }
    
    /**
     * The player rolls a given number on the dice and moves on the field.
     * @param number The rolled number.
     * @param p The active player.
     * @return The type of field where the player landed and his current amount of gold.
     */
    public String roll(int number, Player p) {
        if (!gameWon && p.getCanRoll() && BackersWalzUtility.throwDice(number)) {
            int num = myBoard.moveInField(p, number);
            
            String fieldType = myBoard.fieldType(num);
            int playersGold = p.getGold();
            p.setCanRoll(false);
            p.setCanHarvest(true);
            p.setCanBuy(true);
            p.setCanPrepare(true);
            p.setCanTurn(true);
            
            return fieldType + Main.SEMICOLON_SEPARATOR + playersGold;
            
        } else if (gameWon) {
            return Main.ERROR + Main.PLAYER_WON;
            
        } else if (!p.getCanRoll()) {
            return Main.ERROR + Main.CANT_ROLL;
            
        } else {
            return Main.ERROR + Main.WRONG_NUMBER;
        }
    }
    
    /**
     * The active player can harvest an ingredient from the field where the active player is currently
     * located and sell it to the market.
     * @param p The active player.
     * @return The harvested ingredient and the current amount of gold from the player.
     */
    public String harvest(Player p) {
        if (!gameWon && !myBoard.isStartfield(p.getPosition()) && p.getCanHarvest()
                && myBoard.getField(p.getPosition()).canHarvest(myBoard.getMarket())) {
            
            myBoard.getField(p.getPosition()).harvestMaterial(myBoard.getMarket(), p);
            p.setCanHarvest(false);
            p.setCanBuy(false);
            
            String ressource = myBoard.getField(p.getPosition()).getIngredient();
            int playersGold = p.getGold();
            
            return ressource + Main.SEMICOLON_SEPARATOR + playersGold;
            
        } else if (gameWon) {
            return Main.ERROR + Main.PLAYER_WON;
            
        } else if (myBoard.isStartfield(p.getPosition())) {
            return Main.ERROR + Main.ON_STARTING_FIELD;
            
        } else {
            return Main.ERROR + Main.CANT_HARVEST;
        }
    }
    
    /**
     * The active player can buy ingredients from the market, as long as he has enough gold.
     * @param p The active player.
     * @param ingredient The ingredient the player wants to buy.
     * @return The price the player paid for the material and the players amount of gold after buying
     * the material.
     */
    public String buy(Player p, String ingredient) {
        if (Ingredients.FLOUR.getIngredient().equals(ingredient)
                || Ingredients.EGG.getIngredient().equals(ingredient)
                || Ingredients.MILK.getIngredient().equals(ingredient)) {
            
            if (!gameWon && !myBoard.isStartfield(p.getPosition()) && p.getCanBuy()
                    && myBoard.getMarket().canSell(ingredient)) {
                
                int price = myBoard.getMarket().price(ingredient);
                
                if (p.getGold() >= price) {
                    myBoard.getMarket().sellItem(ingredient);
                    p.boughtMaterial(ingredient);
                    p.addGold(-price);
                    int playersGold = p.getGold();
                    p.setCanHarvest(false);
                    
                    return price + Main.SEMICOLON_SEPARATOR + playersGold;
                    
                } else {
                    return Main.ERROR + Main.NOT_ENOUGH_GOLD;
                }
            } else if (gameWon) {
                return Main.ERROR + Main.PLAYER_WON;
                
            } else if (myBoard.isStartfield(p.getPosition())) {
                return Main.ERROR + Main.ON_STARTING_FIELD;
                
            } else {
                return Main.ERROR + Main.CANT_BUY;
            }
        } else {
            return Main.ERROR + Main.INGREDIENT_NON_EXISTENT;
        }
    }
    
    /**
     * The active player prepares a recipe as long as he has the necessary ingredients. His
     * amount of gold is updated after receiving the earnings.
     * @param p The active player.
     * @param food The recipe that will be prepared.
     * @return The updated amount of gold after preparing the recipe.
     */
    public String prepare(Player p, String food) {
        if (!gameWon && p.getCanPrepare() && BackersWalzUtility.isValidRecipe(food)) {
            
            Baker bakery = new Bakery();
            bakery.prepareSomething(food, p);
            return String.valueOf(p.getGold());
            
        } else if (gameWon) {
            return Main.ERROR + Main.PLAYER_WON;
            
        } else {
            return Main.ERROR + Main.CANT_PREPARE + food + Main.EXCLAMATION_MARK;
        }
    }
    
    /**
     * Shows the recipes the active player can prepare.
     * @param p The active player.
     * @return All recipes the active player can prepare line by line.
     */
    public String canPrepare(Player p) {
        Baker bakery = new Bakery();
        ArrayList<String> canPrepare = bakery.canPrepare(Recipes.RECIPES_ARR, p);
        String temp = "";
        if (!gameWon) {
            for (int i = 0; i < canPrepare.size(); i++) {
                if (i + 1 < canPrepare.size()) {
                    temp += canPrepare.get(i) + "\n";
                    
                } else {
                    temp += canPrepare.get(i);
                }
            }
            return temp;
            
        } else {
            return Main.ERROR + Main.PLAYER_WON;
        }
    }
    
    /**
     * Returns the ingredients line by line sorted by the current amount.
     * @return Sorted ingredients line by line.
     */
    public String showMarket() {
        return myBoard.getMarket().toString();
    }
    
    /**
     * Returns the current gold supply of a selected player.
     * @param playerNum The players number whose supply of gold will be returned.
     * @return The players gold supply.
     */
    public String showPlayer(int playerNum) {
        if (playerNum <= players.length) {
            return players[playerNum - PLAYER_DIFF].toString();
            
        } else {
            return Main.ERROR + Main.PLAYER_NON_EXISTENT;
        }
    }
    
    /**
     * Ends the move of an active player and resets the players status.
     * @param p The active player.
     * @return The new active player.
     */
    public String turn(Player p) {
        if (!gameWon && p.getCanTurn()) {
            p.setCanRoll(true);
            p.setCanHarvest(false);
            p.setCanBuy(false);
            p.setCanPrepare(false);
            p.setCanTurn(false);
            
            int temp = 0;
            for (int i = 0; i < players.length; i++) {
                if (p.equals(players[i])) {
                    temp = i;
                    break;
                }
            }
            
            if (temp + 1 >= players.length) {
                temp = PLAYER_ONE;
                
            } else {
                temp += NEXT_PLAYER_DIFF;
            }
            return Main.PLAYER_LETTER + temp;
            
        } else if (gameWon) {
            return Main.ERROR + Main.PLAYER_WON;
            
        } else {
            return Main.ERROR + Main.CANT_TURN;
        }
    }
    
    /**
     * Finishes the game completely.
     * @param quit The boolean status that decides if the game will be finished
     * or continued.
     */
    public void quit(boolean quit) {
        this.quit = quit;
    }
    
    /**
     * Sets the game to a status in which a player has already won.
     * @param gameWon The boolean status that decides if the game has been won
     * or not.
     */
    public void gameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
    
    /**
     * Checks if a player has reached the necessary amount of gold to win the game. Prints a
     * message for the winner and ends the game.
     * @param p
     */
    public void playerWon(Player p) {
        if (p.hasWon() && !gameWon) {
            Terminal.printLine(Main.PLAYER_LETTER + p.getId() + Main.PLAYER_WIN);
            gameWon(true);
        }
    }
    
    /**
     * Getter for quit.
     * @return The current quit-status in the game.
     */
    public boolean getQuit() {
        return this.quit;
    }
}