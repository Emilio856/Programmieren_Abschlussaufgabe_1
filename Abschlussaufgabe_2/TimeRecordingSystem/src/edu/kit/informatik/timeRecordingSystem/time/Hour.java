package edu.kit.informatik.timeRecordingSystem.time;

import edu.kit.informatik.timeRecordingSystem.Main;

/**
 * Models an hour of the day. A day starts at 00:00 and ends at 23:59. 24:00 is
 * only allowed as ending hour. The class also overrides equals(Object) and compareTo(Hour)
 * to interact with other objects of the type Hour. Modeled as an immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Hour implements Comparable<Hour> {
    
    /**
     * The hour of the day. Starts at 0 and ends at 23.
     */
    private final int hour;
    
    /**
     * The minute in an hour. Starts at 0 and ends at 59.
     */
    private final int minute;
    
    /**
     * Constructor for an hour. initializes the final attributes of the class.
     * @param hour The hours of the hour.
     * @param minute The minutes of the hour.
     */
    public Hour(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
    
    /**
     * Overrides the equals method to compare two hours. Returns true
     * if the hour is compared to itself or both hours have equal attributes.
     */
    @Override
    public boolean equals(Object anHour) {
        if (anHour == this) {
            return true;
        }
        
        if (anHour == null) {
            return false;
        }
        
        if (!(anHour instanceof Hour)) {
            return false;
        }
        
        Hour hour = (Hour) anHour;
        return Integer.compare(this.hour, hour.hour) == 0
                && Integer.compare(this.minute, hour.minute) == 0;
    }
    
    /**
     * Overrides hashCode for an hour.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Overrides the toString method to fit the dates format. Adds leading zeros
     * to the hour and minute to fit the specifications.
     */
    @Override
    public String toString() {
        String newHour = String.format("%02d", hour);
        String newMinute = String.format("%02d", minute);
        
        return newHour + Main.HOUR_SEPARATOR + newMinute;
    }
    
    /**
     * Overrides the compareTo method to compare two objects of the type Hour.
     * Checks if the hour h comes before, at the same time or after this.
     */
    @Override
    public int compareTo(Hour h) {
        int hourDiff = hour - h.getHour();
        if (hourDiff == 0) {
            return minute - h.minute;
        }
        return hourDiff;
    }
    
    /**
     * Getter for the hours hour.
     * @return The hours of the hour.
     */
    public int getHour() {
        return this.hour;
    }
    
    /**
     * Getter for the minutes of the hour.
     * @return The hours minutes.
     */
    public int getMinute() {
        return this.minute;
    }
}