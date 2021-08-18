package edu.kit.informatik.timeRecordingSystem.time;

import edu.kit.informatik.timeRecordingSystem.DateUtility;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.WorkDay;

/**
 * Models a time consisting of a date and an hour. The class also overrides
 * equals(Object) and compareTo(Time) to interact with other objects of the
 * same type. Modeled as an immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Time implements Comparable<Time> {
    
    /**
     * Used for conversion purposes from day to hours.
     */
    public static final int CALCULATION_RULES_24 = 24;
    
    /**
     * Used for conversion purposes from hour to minutes.
     */
    public static final int CALCULATION_RULES_60 = 60;
    
    /**
     * Used to simplify the calculation of time between two moments.
     */
    public static final int UTILITY_HOURS = 1;
    
    /**
     * The date of the time containing day, month and year.
     */
    private final Date date;
    
    /**
     * The hour of the time containing hours and minute.
     */
    private final Hour hour;
    
    /**
     * Constructor for a time. Receives a date and an hour as parameters
     * to initialize its attributes.
     * @param date The date of the time.
     * @param hour The hour of the time.
     */
    public Time(Date date, Hour hour) {
        this.date = date;
        this.hour = hour;
    }
    
    /**
     * Overrides the equals method to compare two times. Returns true if the
     * time is compared to itself or both times have equal date and hour.
     */
    @Override
    public boolean equals(Object aTime) {
        if (aTime == this) {
            return true;
        }
        
        if (aTime == null) {
            return false;
        }
        
        if (!(aTime instanceof Time)) {
            return false;
        }
        
        Time time = (Time) aTime;
        return date.equals(time.date) && hour.equals(time.hour);
    }
    
    /**
     * Overrides hashCode for a time.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Overrides the toString method to fit the times format. Mixes the toString
     * methods from Date and Hour and separates them.
     */
    @Override
    public String toString() {
        return date.toString() + Main.DATE_HOUR_SEPARATOR + hour.toString();
    }
    
    /**
     * Overrides the compareTo method to compare two objects of the type Time.
     * Mixes compareTo from Date and Hour to check if Time t comes before, at
     * the same time or after this.
     */
    @Override
    public int compareTo(Time t) {
        if (DateUtility.nextDay(t.date).equals(date)
                && hour.equals(WorkDay.START_DAY)
                && t.getHour().equals(WorkDay.END_DAY)) {
            return 0;
        } else if (DateUtility.nextDay(date).equals(t.date)
                && hour.equals(WorkDay.END_DAY)
                && t.getHour().equals(WorkDay.START_DAY)) {
            return 0;
        } else  if (date.compareTo(t.getDate()) == 0) {
            return hour.compareTo(t.getHour());
        }
        return date.compareTo(t.getDate());
    }
    
    /**
     * Getter for the times date. Allows then to access the dates getters and methods.
     * @return The times date.
     */
    public Date getDate() {
        return this.date;
    }
    
    /**
     * Getter for the times hour. Allows then to access the hours getters and methods.
     * @return The times hour.
     */
    public Hour getHour() {
        return this.hour;
    }
}