package edu.kit.informatik.bakerswalz.game;

import java.util.ArrayList;

import edu.kit.informatik.bakerswalz.game.recipes.Recipes;
import edu.kit.informatik.bakerswalz.ui.Main;

/**
 * Utility class for Bäkers Walz. Contains methods that are used to verify a valid game board,
 * check recipes and rolled dices. Modeled as a utility class because it's methods are only used
 * in the class Commands and make some part of the code easier to understand.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public final class BackersWalzUtility {
    
    /**
     * Maximal amount of starting field a board can have.
     */
    private static final int MAX_STARTING_FIELDS = 1;
    
    /**
     * Minimal size of a board.
     */
    private static final int MIN_BOARDSIZE = 4;
    
    /**
     * Maximal size of a board.
     */
    private static final int MAX_BOARDSIZE = 25;
    
    /**
     * Maximal distance two field of the same type can have.
     */
    private static final int MATERIALS_MAX_DISTANCE = 4;
    
    /**
     * First after starting field.
     */
    private static final int INDEX_ONE = 1;
    
    /**
     * Abbreviation for a starting field.
     */
    private static final String STARTING_FIELD = "S";
    
    /**
     * Abbreviation for a mill field
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
     * Private constructor for GameBoardUtility as it's a utility class.
     */
    private BackersWalzUtility() {
        
    }
    
    /**
     * Checks that the board respects the rule of the minimal needed size.
     * @param fieldArray The Array containing the information about the fields of the board.
     * @return True if the boards length is equal or bigger than the needed length.
     */
    public static boolean minimalSize(String[] fieldArray) {
        return fieldArray.length >= MIN_BOARDSIZE;
    }
    
    /**
     * Checks that the board respects the rule of the maximal size.
     * @param fieldArray The Array containing the information about the fields of the board.
     * @return True if the boards length is equals or less than the maximal allowed size.
     */
    public static boolean maximalSize(String[] fieldArray) {
        return fieldArray.length <= MAX_BOARDSIZE;
    }
    
    /**
     * Checks if a field on the board is a starting field.
     * @param string The value of the Field.
     * @return True if string is a starting field
     */
    public static boolean validStartingField(String string) {
        if (string.equals(STARTING_FIELD)) {
            return true;
            
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the board contains only one starting field.
     * @param fieldArray The Array containing the information about the fields of the board.
     * @return True if the board only contains one starting  field, else false.
     */
    public static boolean hasOneStartingField(String[] fieldArray) {
        int counter = 0;
        
        for (String str : fieldArray) {
            if (str.equals(STARTING_FIELD) && counter < MAX_STARTING_FIELDS) {
                counter++;
                
            } else if (str.equals(STARTING_FIELD) && counter == MAX_STARTING_FIELDS) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the board contains at least one field of the given type. Ends the search
     * after finding one matching field.
     * @param fieldArray The array containing the information about the fields of the board.
     * @param fieldType The type of field that will be looked for.
     * @return True if one matching field is found.
     */
    public static boolean hasMaterialField(String[] fieldArray, String fieldType) {
        for (String str : fieldArray) {
            if (str.equals(fieldType)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Analyzes the board fields to check that they are all valid fields.
     * @param fieldArray Contains the information about the fields of the board.
     * @return True if all fields are valid fields.
     */
    public static boolean validFields(String[] fieldArray) {
        for (String str : fieldArray) {
            if (str.equals(STARTING_FIELD) || str.equals(MILL_FIELD) || str.equals(HENHOUSE_FIELD)
                    || str.equals(PASTURE_FIELD)) {
                continue;
                
            } else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Two neighbor fields cannot be of the same type. The method analyzes the board to make
     * sure this rule is being followed.
     * @param fieldArray The array containing the information about the fields of the board.
     * @return True if no  neighbor fields are of the same type.
     */
    public static boolean neighborFields(String[] fieldArray) {
        ArrayList<String> newArray = new ArrayList<String>();
        
        //Last element of fieldArray at start
        newArray.add(fieldArray[fieldArray.length - 1]);
        
        //All elements of fieldArray
        for (int i = 1; i < fieldArray.length; i++) {
            newArray.add(fieldArray[i]);
        }
        
        //First element of fieldArray at end
        newArray.add(fieldArray[INDEX_ONE]);
        
        for (int i = 1; i < newArray.size() - 1; i++) {
            if (newArray.get(i).equals(newArray.get(i - 1)) || newArray.get(i).equals(newArray.get(i + 1))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if every field has a maximal distance of four fields
     * to the next field of the same type as this is an important
     * rule for the game board.
     * @param fieldArray The array containing the information 
     * @return True if every field has a field of the same type at a
     * distance of maximal four fields.
     */
    public static boolean fourFieldsRule(String[] fieldArray) {
            
        ArrayList<String> newArray = new ArrayList<>();
        
        //Last 4 elements of fieldArray at start
        for (int i = fieldArray.length - MATERIALS_MAX_DISTANCE; i < fieldArray.length; i++) {
            newArray.add(fieldArray[i]);
        }
        
        //All elements of fieldArray
        for (int i = 1; i < fieldArray.length; i++) {
            newArray.add(fieldArray[i]);
        }
        
        //First element of fieldArray at end
        for (int i = 1; i <= MATERIALS_MAX_DISTANCE; i++) {
            newArray.add(fieldArray[i]);
        }
        
        for (int i = MATERIALS_MAX_DISTANCE; i < newArray.size() - MATERIALS_MAX_DISTANCE; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = i - MATERIALS_MAX_DISTANCE; j <= i + MATERIALS_MAX_DISTANCE; j++) {
                temp.add(newArray.get(j));
            }
            if (!fourFieldsTemp((temp.toArray(new String[temp.size()])), newArray.get(i))) {
                return false;
            }
        }
        return true;
    }
        
    /**
     * Checks for a specific type if it has a field of the same
     * type in a range of four fields.
     * @param arr The array containing the field of the specific type
     * and all other fields in its allowed range.
     * @param type The type of the field.
     * @return True if this field has another field of the same type
     * in the allowed range,
     */
    private static boolean fourFieldsTemp(String[] arr, String type) {
        boolean flagBackwards = false;
        boolean flagForward = false;
        
        for (int i = 0; i < MATERIALS_MAX_DISTANCE; i++) {
            if (arr[i].equals(type)) {
                flagBackwards = true;
                break;
                
            } else {
                continue;
            }
        }
        
        if (!flagBackwards) {
            flagForward = fourFieldsForward(arr, type);
        }
        
        return flagBackwards || flagForward;
    }
    
    /**
     * Help method for fourFieldsTemp(String[], String). Analyzes
     * the fields that come after the currently observed field inside
     * the allowed range.
     * @param arr The array containing the field of the specific type
     * and all other fields in its allowed range.
     * @param type The type of the field.
     * @return True if there is a matching field in the forward range.
     */
    private static boolean fourFieldsForward(String[] arr, String type) {
        boolean flag = false;
        
        for (int i = MATERIALS_MAX_DISTANCE + 1; i < arr.length; i++) {
            if (arr[i].equals(type)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Checks if an input is a valid recipe.
     * @param recipe The input that will be analyzed.
     * @return True if the input is a valid recipe.
     */
    public static boolean isValidRecipe(String recipe) {
        for (String str : Recipes.RECIPES_ARR) {
            if (str.equals(recipe)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks that a thrown dice shows a valid number from one to six.
     * @param num The thrown number.
     * @return True if num is between one and six.
     */
    public static boolean throwDice(int num) {
        if (Main.DICE_LOWER_BOUND <= num && num <= Main.DICE_UPPER_BOUND) {
            return true;
            
        } else {
            return false;
        }
    }
}