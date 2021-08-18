package edu.kit.informatik.bakerswalz.game;

import java.util.ArrayList;
import edu.kit.informatik.bakerswalz.game.board.Henhouse;
import edu.kit.informatik.bakerswalz.game.board.Mill;
import edu.kit.informatik.bakerswalz.game.board.Pasture;
import edu.kit.informatik.bakerswalz.game.board.Start;
import edu.kit.informatik.bakerswalz.game.board.PlayingField;
import edu.kit.informatik.bakerswalz.ui.Main;

/**
 * Models a game board players can move around during a game. Checks if an input is capable of
 * instantiating a valid board based on the rules of the game.
 * 
 * Contains a single starting field and at least one henhouse, mill and pasture. The player can
 * interact with all of these fields to harvest materials, get rewards for landing on a starting field
 * and buy ingredients from the market.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class GameBoard {
    
    /**
     * Critical length for the 4 fields rule. With this board length it's
     * impossible for a field to not be within four fields of a field of
     * the same type.
     */
    private static final int CRITICAL_LENGTH = 5;
    
    /**
     * Abbreviation for a starting field.
     */
    private static final String STARTING_FIELD = "S";
    
    /**
     * Abbreviation for a mill field.
     */
    private static final String MILL_FIELD = "M";
    
    /**
     * Abbreviation for a henhouse field.
     */
    private static final String HENHOUSE_FIELD = "H";
    
    /**
     * Abbreviation for a pasture field.
     */
    private static final String PASTURE_FIELD = "C";
    
    /**
     * Representation of the board as an ArrayList.
     */
    private ArrayList<PlayingField> myBoard;
    
    /**
     * The market for a game.
     */
    private Market market;
    
    /**
     * Constructor for a GameBoard.
     * @param boardInformation The information about the fields on the board.
     */
    public GameBoard(String boardInformation) {
        if (validBoard(boardInformation)) {
            myBoard = new ArrayList<>();
            market = new Market();
            String[] input = boardInformation.split(Main.SEMICOLON_SEPARATOR);
            
            for (String str : input) {
                myBoard.add(initiateBoard(str));
            }
        }
    }
    
    /**
     * Checks if a board fulfills all required conditions.
     * @param input The information about the board.
     * @return True if the board fulfills all requirements and therefore
     * is a valid board for the game.
     */
    public boolean validBoard(String input) {
        String[] newInput = input.split(Main.SEMICOLON_SEPARATOR);
        
        if (BackersWalzUtility.minimalSize(newInput) && BackersWalzUtility.maximalSize(newInput)
                && BackersWalzUtility.validStartingField(newInput[0])
                && BackersWalzUtility.hasOneStartingField(newInput)
                && BackersWalzUtility.hasMaterialField(newInput, MILL_FIELD)
                && BackersWalzUtility.hasMaterialField(newInput, HENHOUSE_FIELD)
                && BackersWalzUtility.hasMaterialField(newInput, PASTURE_FIELD)
                && BackersWalzUtility.validFields(newInput)
                && BackersWalzUtility.neighborFields(newInput)) {
            
            if (newInput.length >= CRITICAL_LENGTH) {
                return BackersWalzUtility.fourFieldsRule(newInput);
                
            } else {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Initiates a field of a certain type for the game board.
     * @param str The abbreviation for the type of field to initiate.
     * @return A field of the specified type.
     */
    private PlayingField initiateBoard(String str) {
        PlayingField field = null;
        if (str.equals(STARTING_FIELD)) {
            field = new Start();
            
        } else if (str.equals(MILL_FIELD)) {
            field = new Mill();
            
        } else if (str.equals(HENHOUSE_FIELD)) {
            field = new Henhouse();
            
        } else if (str.equals(PASTURE_FIELD)) {
            field = new Pasture();
        }
        return field;
    }
    
    /**
     * Moves a player around the field.
     * @param p The player.
     * @param rolledNumber The rolled number for the amount of fields
     * the player will move.
     * @return The number of the field the player landed on.
     */
    public int moveInField(Player p, int rolledNumber) {
        int fieldsLeft = rolledNumber;
        int currentPosition = p.getPosition();
        
        while (fieldsLeft > 0) {
            if (currentPosition + 1 < myBoard.size()) {
                currentPosition++;
                fieldsLeft--;
                
            } else {
                currentPosition = 0;
                fieldsLeft--;
            }
        }
        p.setPosition(currentPosition);
        p.addGold(myBoard.get(currentPosition).earnedGold());
        return currentPosition;
    }
    
    /**
     * Checks the type of a field on a certain position.
     * @param pos The position of the field to be analyzed.
     * @return The abbreviation of the type of field at the specified position.
     */
    public String fieldType(int pos) {
        Start start = new Start();
        Henhouse henhouse = new Henhouse();
        Mill mill = new Mill();
        Pasture pasture = new Pasture();
        
        if (start.equals(myBoard.get(pos))) {
            return STARTING_FIELD;
            
        } else if (henhouse.equals(myBoard.get(pos))) {
            return HENHOUSE_FIELD;
            
        } else if (mill.equals(myBoard.get(pos))) {
            return MILL_FIELD;
            
        } else if (pasture.equals(myBoard.get(pos))) {
            return PASTURE_FIELD;
            
        } else {
            return null;
        }
    }
    
    /**
     * Checks if a field at a certain position is a starting field.
     * @param pos The position of the field to be analyzed.
     * @return True if the specified field is a starting field.
     */
    public boolean isStartfield(int pos) {
        Start start = new Start();
        if (start.equals(myBoard.get(pos))) {
            return true;
        }
        return false;
    }
    
    /**
     * Getter for a PlayingField at a specified position on the board.
     * @param pos The position of the field.
     * @return The PlayingField at the position.
     */
    public PlayingField getField(int pos) {
        return this.myBoard.get(pos);
    }
    
    /**
     * Getter for the market of a board.
     * @return The market of the game board.
     */
    public Market getMarket() {
        return this.market;
    }
}