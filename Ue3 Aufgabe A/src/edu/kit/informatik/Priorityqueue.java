package edu.kit.informatik;

import java.util.ArrayList;

/**
 * Public class for a Priority Queue.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Priorityqueue {
    
    /**
     * Priority queue
     */
    ArrayList<Song> priorityQueue = new ArrayList<Song>();
    
    /**
     * Overrides toString() for Priority queue.
     */
    @Override
    public String toString() {
        String list = "";
        for (Song s : priorityQueue) {
            if (priorityQueue.indexOf(s) + 1 < priorityQueue.size()) {
                list = list + String.format("%05d", s.getID()) + ":" + s.getArtist() + ":" + s.getSongName()
                    + ":" + s.getLength() + "\n";
            }
            else {
                list = list + String.format("%05d", s.getID()) + ":" + s.getArtist() + ":" + s.getSongName()
                    + ":" + s.getLength();
            }
        }
        return list;
    }
}