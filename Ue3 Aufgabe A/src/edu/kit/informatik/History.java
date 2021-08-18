package edu.kit.informatik;

import java.util.ArrayList;

/**
 * Public class for a history queue.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class History {
    
    /**
     * History queue
     */
    ArrayList<Song> history = new ArrayList<Song>();
    
    /**
     * Overrides toString() for History queue.
     */
    @Override
    public String toString() {
        String historySongs = "";
        for (Song s : history) {
            if (history.indexOf(s) + 1 < history.size()) {
                historySongs = historySongs + String.format("%05d", s.getID()) + ":" + s.getArtist() + ":"
                    + s.getSongName() + ":" + s.getLength() + "\n";
            }
            else {
                historySongs = historySongs + String.format("%05d", s.getID()) + ":" + s.getArtist() + ":"
                    + s.getSongName() + ":" + s.getLength();
            }
        }
        return historySongs;
    }
}