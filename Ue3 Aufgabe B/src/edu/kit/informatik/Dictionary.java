package edu.kit.informatik;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary {
    private boolean quit = false;
    HashMap<String, String> dictionary = new HashMap<String, String>();
    ArrayList<Word> dict = new ArrayList<Word>();
    ArrayList<ArrayList<String>> bla = new ArrayList<>();
    SortByAlphabet sorter = new SortByAlphabet();
    
    public void add(String word1, String word2) {
        //dictionary.put(word1, word2);
        //sortByAlphabet();
        //Collections.sort(dictionary, Collator.getInstance(Locale.GERMAN));
        
        Word newWord = new Word(word1, word2);
        dict.add(newWord);
        String g = newWord.getStartLanguage();
        //Collections.sort(dict, Collator.getInstance(Locale.GERMAN));
        dict = sortA();
        //dict.sort(Comparator.naturalOrder());
    }
    
    public void print() {
        for (Word w : dict) {
            Terminal.printLine(w.getStartLanguage() + " - " + w.getTargetLanguage());
        }
    }
    
    public ArrayList<Word> sortA() {
        //Collections.sort(dict, Collator.getInstance(Locale.GERMAN));
        Collections.sort(dict, sorter);
        return dict;
    }
    
    
  //Setter for quit
    public void setQuit(boolean quit) {
        this.quit = quit;
    }
    
    //Getter for quit
    public boolean getQuit() {
        return this.quit;
    }
    
    
    public void sortByAlphabet() {
        TreeMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.putAll(dictionary);
    }
    
    public static void sortByLetters() {
        //Collections.sort(dictionary, );
    }
    
    

}