package edu.kit.informatik;

import java.util.Collections;

/**
 * Implements methods to use on a Priority Queue that
 * consists of Objects of the type Song.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Commands {
    
    /**
     * Priority Queue
     */
    Priorityqueue queue = new Priorityqueue();
    
    /**
     * Stores already played songs
     */
    History history = new History();
    
    /**
     * Controls if program has to be ended.
     */
    private boolean quit = false;
    
    /**
     * Adds a new song to the queue.
     * @param id ID of the song.
     * @param artist Artist of the song.
     * @param songName Name of the song.
     * @param length Length of the song.
     * @param priority Priority of the song.
     * @return True if the new Song was added
     */
    public boolean add(int id, String artist, String songName, int length, int priority) {
        boolean containsID = false;
        boolean flag = false;
        int index = 0;
        Song newSong = new Song(id, artist, songName, length, priority);
        
        //Checks for duplicates of the song if ID already exists
        if (queue.priorityQueue.size() > 0) {
            for (Song s : queue.priorityQueue) {
                if (s.getID() == id) {
                    containsID = true;
                    index = queue.priorityQueue.indexOf(s);
                    break;
                }
            }
            if (containsID && !newSong.equals(queue.priorityQueue.get(index))) {
                flag = true;
            }
        }
        
        if (!flag) {
            
            //First song is already running
            if (queue.priorityQueue.size() > 0
                    && queue.priorityQueue.get(0).getTime() != queue.priorityQueue.get(0).getLength()) {
                queue.priorityQueue.add(1, newSong);
                Collections.sort(queue.priorityQueue);
                return true;
            }
            
            //First song not running yet
            else {
                queue.priorityQueue.add(newSong);
                Collections.sort(queue.priorityQueue);
                return true;
            }
        }
        else {
            return false;
        }
    }
    
    /**
     * Removes all Songs with the ID id.
     * @param id The ID of the Songs that will be deleted.
     * @return Amoount of removed songs
     */
    public int remove(int id) {
        int removed = 0;
        if (isValidID(id)) {
            
            for (Song s : queue.priorityQueue) {
                if (s.getID() == id) {
                    removed++;
                }
            }
            queue.priorityQueue.removeIf(n -> (n.getID() == id));
        }
        return removed;
    }
    
    /**
     * Plays radio for given amount of time. Finished songs are added to the history.
     * @param length Time the radio will be played.
     */
    public void play(int length) {
        if (isValidLength(length) && !queue.priorityQueue.isEmpty()) {
            int counter = length;
            while (counter > 0 && !queue.priorityQueue.isEmpty()) {
                int playSong = queue.priorityQueue.get(0).getTime();
                playSong--;
                counter--;
                queue.priorityQueue.get(0).setTime(playSong);
                if (queue.priorityQueue.get(0).getTime() == 0) {
                    history.history.add(queue.priorityQueue.get(0));
                    queue.priorityQueue.remove(0);
                }
                else {
                    continue;
                }
            }
        }
    }
    
    /**
     * Skips a song.
     */
    public void skip() {
        if (!queue.priorityQueue.isEmpty()) {
            queue.priorityQueue.remove(0);
        }
    }
    
    /**
     * Returns the information about the next song in the queue.
     * @return Information about the next song in the queue.
     */
    public String peek() {
        String peekSong = "";
        if (!queue.priorityQueue.isEmpty()) {
            peekSong = String.format("%05d", queue.priorityQueue.get(0).getID()) + ":"
                    + queue.priorityQueue.get(0).getArtist() + ":" + queue.priorityQueue.get(0).getSongName()
                    + ":" + queue.priorityQueue.get(0).getLength() + ":" + queue.priorityQueue.get(0).getTime();
        }
        return peekSong;
    }
    
    /**
     * Checks if the ID is valid.
     * @param id ID number to be analyzed.
     * @return True if the ID is valid.
     */
    public boolean isValidID(int id) {
        return 0 < id && id < Math.pow(15, 5);
    }
    
    /**
     * Checks if the length of the song is valid.
     * @param length Length to be analyzed.
     * @return True if the length is valid.
     */
    public boolean isValidLength(int length) {
        return 0 < length && length < Math.pow(2, 31);
    }
    
    /**
     * Checks if the priority of the song is valid.
     * @param priority Priority to be analyzed.
     * @return True if the priority is valid.
     */
    public boolean isValidPriority(int priority) {
        return 0 <= priority && priority <= 5;
    }
    
    /**
     * Setter for quit
     * @param quit True to quit program
     */
    public void setQuit(boolean quit) {
        this.quit = quit;
    }
    
    /**
     * Getter for quit
     * @return value of quit.
     */
    public boolean getQuit() {
        return this.quit;
    }
}