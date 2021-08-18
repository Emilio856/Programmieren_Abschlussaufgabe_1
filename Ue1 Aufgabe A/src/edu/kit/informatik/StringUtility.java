package edu.kit.informatik;

/**
 * Works as Utility Class and contains various methods 
 * to analyze Strings.
 * @author Emilio Rivera
 *
 * Tutor: Könntest Du mir bitte Feedback zu meinen Kommentaren geben?
 * Ich weiß nicht genau, ob ich zu viel kommentiere und ob es gut 
 * formuliert ist was ich schreibe. Vielen Dank schon im voraus! 
 * @version 1.0
 */
public final class StringUtility {

    /**
     * Private constructor of the Utility class.
     * Tutor: Laut dem Programmieren Wiki muss eine Utility Klasse einen
     * private Konstruktor haben. Ist das so richtig?
     */
    private StringUtility() {
    }
    
    /**
     * Reverses the given String and returns it.
     * @param word The String that will be reversed.
     * @return A reversed version of the input String.
     */
    public static String reverse(String word) {   
        //Splits String word and puts it in an Array
        String[] inputWord = word.split("");
        String reversedWord = "";
        int wordLength = inputWord.length;
        
        //Reverts the Array of the word
        String[] reverseArray = new String[wordLength]; 
        if (inputWord.length > 1) {
            for (int i = 0; i < wordLength / 2; i++) {
                String value = inputWord[i];
                inputWord[i] = inputWord[wordLength - i - 1];
                inputWord[wordLength - i - 1] = value;
                reverseArray = inputWord;
            }
        }
        else {
            reverseArray[0] = inputWord[0];
        }
        
        //Forms the reversed String with the reversed Array
        for (int i = 0; i < wordLength; i++) {
            reversedWord += reverseArray[i];
        }
        
        return reversedWord;
    }

    /**
     * Checks if the given String is a palindrome or not, meaning that it
     * can be read the same way forwards and backwards.
     * @param word The String that will be analyzed.
     * @return True, if the String is a palindrome.
     */
    public static boolean checkPalindrome(String word) {
        String[] inputWord = word.split("");
        boolean palindrome = true;
        
        /*compares mirrored characters of the String to check if they are
         * all the same or not*/
        for (int i = 0; i < inputWord.length / 2; i++) {
            if (inputWord[i].equals(inputWord[inputWord.length - i - 1])) {
                continue;
            }
            else {
                palindrome = false;
                break;
            }
        }
        return palindrome;
    }
    
    /**
     * Removes a character from a word at an index.
     * @param word The String that will loose a character.
     * @param index The Index from the character that will be deleted
     * from the String.
     * @return The new String without the removed character.
     */
    public static String removeCharacter(String word, int index) {
        String[] inputWord = word.split("");
        String newWord = "";
        
        /*Sets the content in index of Array as null and forms the
         * new Array ignoring nulls */
        if (inputWord.length >= index) {
            inputWord[index] = null;
            for (int i = 0; i < inputWord.length; i++) {
                if (inputWord[i] != null) {
                    newWord += inputWord[i];
                }
            }
        }      
        
        return newWord;
    }
    
    /**
     * Checks if the given Strings are Anagrams of each other.
     * @param word1 The first String.
     * @param word2 The second String.
     * @return True, if both parameters are Anagrams of each other.
     */
    public static boolean checkAnagram(String word1, String word2) {
        boolean anagram = true;
        
        //Deletes all whitespace in the parameters
        String newWord1 = word1.replaceAll("\\s", "");
        String newWord2 = word2.replaceAll("\\s", "");
        
        if (newWord1.length() == newWord2.length()) {
            char[] w1Array = newWord1.toCharArray();
            
            /*Goes through all characters from word1 once and searches for them
             * in word2. If the character is found it deletes it from word2 and
             * rearrenges the String of the second parameter excluding the already
             * found characters*/
            for (char ch : w1Array) {
                int index = newWord2.indexOf(ch);
                if (index != -1) {
                    newWord2 = newWord2.substring(0, index)
                            + newWord2.substring(index + 1, newWord2.length());
                }
                
                //Parameters aren't anagrams if one of the characters isn't found
                else {
                    anagram = false;
                    break;
                }
            }
        }
        else {
            anagram = false;
        }
        
        return anagram;
    }
    
    /**
     * Peplaces the first letter of a String with a lowercase.
     * @param word The word of which the first letter will be
     * replaced with a lowercase.
     * @return The new String with a lowercase at the beginning.
     */
    public static String lowercase(String word) {
        String[] inputWord = word.split("");
        String newWord = "";
        
        //Lowercase first character of word and form the new String
        inputWord[0] = inputWord[0].toLowerCase();
        for (int i = 0; i < inputWord.length; i++) {
            newWord += inputWord[i];
        }     
        return newWord;
    }
    
    /**
     * Counts the times a given character appears in a given Word.
     * @param word The word that will be analyzed.
     * @param character The character that will be searched in the
     * first parameter.
     * @return The times the character appears in the given String.
     */
    public static int countCharacter(String word, char character) {
        char[] charArray = word.toCharArray();
        
        //Counts +1 if the character is found in the word
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (charArray[i] == character) {
                count++;
            }
        }
        return count;
    }
}
