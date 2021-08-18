package edu.kit.informatik;

import java.util.Objects;

/**
 * Implements methods to analyze if a String is a pangram or an isogram.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Holoalphabetic {
    private boolean quit = false;
    private final char[] alphabet = " abcdefghijklmnopqrstuvwxyz".toCharArray();
    private int[] counterArray = new int[27];
    
    /**
     * Sets all letters to lower case and counts how often they appear
     * in the given String.
     * @param sentence The String that will be analyzed. 
     */
    public void letterCounter(String sentence) {
        char[] sentenceArray = sentence.toCharArray();
        
        //Sentence Array
        for (int i = 0; i < sentence.length(); i++) {
            String lower = Character.toString(sentenceArray[i]);
            lower = lower.toLowerCase();
            sentenceArray[i] = lower.charAt(0);

            //Alphabet Array
            for (int j = 1; j < alphabet.length; j++) {              
                if (Objects.equals(sentenceArray[i], alphabet[j])) {
                    counterArray[j]++;
                    break;
                }
            }
        }
    }
    
    /**
     * Checks if all letters of the alphabet appear at least once in the String and
     * therefore the String is a pangram.
     * @return True if the String is a pangram.
     */
    public boolean isPangram() {
        boolean flag = false;
        for (int i = 1; i < counterArray.length; i++) {
            
            if (counterArray[i] >= 1) {
                flag = true;
                continue;
            }
            else {
                flag = false;
                break;
            }           
        }
        return flag;
    }

    /**
     * Checks if all letters of the alphabet appear exactly once in the String and
     * therefore the String is an isogram.
     * @return True if the String is an isogram.
     */
    public boolean isIsogram() {
        boolean flag = false;
        for (int i = 1; i < counterArray.length; i++) {
            
            if (counterArray[i] == 1) {
                flag = true;
                continue;
            }
            else {
                flag = false;
                break;
            }      
        }
        return flag;
    }
    
    /**
     * Resets the array that counts the letters.
     */
    public void reset() {
        for (int i = 0; i < counterArray.length; i++) {
            counterArray[i] = 0;
        }
    }
    
    //Getter for quit.
    public boolean getQuit() {
        return this.quit;
    }
    
    //Setter for quit.
    public void setQuit(boolean quit) {
        this.quit = quit;
    } 
}