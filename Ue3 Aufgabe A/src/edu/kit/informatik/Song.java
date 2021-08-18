package edu.kit.informatik;

import java.util.Objects;

/**
 * Public class for Song.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Song implements Comparable<Song> {
    
    /**
     * The ID of the song. Has to be element of (0, 15^5).
     */
    private final int id;
    
    /**
     * The priority the song has. Goes from 0 (highest) to
     * 5 (lowest).
     */
    private final int priority;
    
    /**
     * The name of the artist.
     */
    private final String artist;
    
    /**
     * The name of the song.
     */
    private final String songName;
    
    /**
     * The length of the song. Has to be element of (0, 2^31).
     */
    private final int length;
    
    /**
     * The remaining time of the song. Has to be element of (0, 2^31).
     * When instancing a new song "time" is equal to "length".
     */
    private int time;
    
    /**
     * Constructor for a Song.
     * @param id ID of the song.
     * @param artist Artist of the song.
     * @param songName Name of the song.
     * @param length Length of the song.
     * @param priority Priority of the song.
     */
    public Song(int id, String artist, String songName, int length, int priority) {
        this.id = id;
        this.artist = artist;
        this.songName = songName;
        this.length = length;
        this.priority = priority;
        this.time = length;
    }
    
    /**
     * Getter for priority.
     * @return Integer priority.
     */
    public int getPriority() {
        return this.priority;
    }
    
    /**
     * Getter for id.
     * @return Integer id.
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Getter for artist.
     * @return String artist.
     */
    public String getArtist() {
        return this.artist;
    }
    
    /**
     * Getter for songName.
     * @return String songName.
     */
    public String getSongName() {
        return this.songName;
    }
    
    /**
     * Getter for length.
     * @return Integer length.
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * Getter for time.
     * @return Integer time.
     */
    public int getTime() {
        return this.time;
    }
    
    /**
     * Setter for time.
     * @param time Integer time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Overrides compareTo method. Sorts Songs by priority
     * in ascending order.
     */
    @Override
    public int compareTo(Song o) {
        int comparePriority = ((Song) o).getPriority();
        return this.priority - comparePriority;
    }
    
    /**
     * Overrides equals method. Compares two songs and returns
     * true if both songs are equal.
     */
    @Override
    public boolean equals(Object aSong) {
        //True if object is compared with itself
        if (aSong == this) {
            return true;
        }
        
        //Checks null
        if (aSong == null) {
            return false;
        }
        
        //Checks type + cast
        if (!(aSong instanceof Song)) {
            return false;
        }
        Song song = (Song) aSong;
        
        return Integer.compare(id, song.id) == 0 && Objects.equals(artist, song.artist)
                && Objects.equals(songName, song.songName) && Integer.compare(length, song.length) == 0
                    && Integer.compare(priority, song.priority) == 0;
    }
}