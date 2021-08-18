package edu.kit.informatik;

import java.util.Collections;
import java.util.Comparator;

public class SortByAlphabet implements Comparator<Word> {

    @Override
    public int compare(Word w1, Word w2) {
        if (w1 == w2) {
            return 0;
        }
        
        if (w1 == null) {
            return -1;
        }
        
        if (w2 == null) {
            return -1;
        }
        
        if (w1.getStartLanguage().charAt(0) == 'ä' && w2.getStartLanguage().charAt(0) == 'a') {
            
        }
        
        return w1.getStartLanguage().compareTo(w2.getStartLanguage());
    }

}
