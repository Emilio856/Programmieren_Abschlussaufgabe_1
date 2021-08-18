package edu.kit.informatik.timeRecordingSystem.time;

import edu.kit.informatik.timeRecordingSystem.DateUtility;
import edu.kit.informatik.timeRecordingSystem.Main;

/**
 * Models a date of the calendar. Dates can be between year 1000 and
 * year 9999. A date consists of a day, a month and a year. This class
 * also overrides equals(Object) and compareTo(Date) to interact with other
 * Objects of the type Date. Modeled as an immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Date implements Comparable<Date> {
    
    /**
     * Used to calculate if a year is a leap year dividing by modulo 400.
     */
    public static final int LEAP_YEAR_MODULO_400 = 400;
    
    /**
     * Used to calculate if a year is a leap year dividing by modulo 4.
     */
    public static final int LEAP_YEAR_MODULO_4 = 4;
    
    /**
     * Used to calculate if a year is a leap year dividing by modulo 100.
     */
    public static final int LEAP_YEAR_MODULO_100 = 100;
    
    /**
     * The lower bound for the allowed years.
     */
    public static final int YEAR_LOWER_BOUND = 1000;
    
    /**
     * The upper bound for the allowed years.
     */
    public static final int YEAR_UPPER_BOUND = 9999;
    
    /**
     * The day of the date.
     */
    private final int day;
    
    /**
     * The month of the date.
     */
    private final int month;
    
    /**
     * The year of the date.
     */
    private final int year;
    
    /**
     * Defines if a date is a leap year or not.
     */
    private final boolean isLeapYear;
    
    /**
     * Constructor for a date. Uses the parameters to calculate if the year
     * is a leap year.
     * @param day The day of the date.
     * @param month The month of the date.
     * @param year The year of the date.
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.isLeapYear = DateUtility.isLeapYear(year);
    }
    
    /**
     * Overrides the equals method to compare two dates. Returns true
     * if the date is compared to itself or both dates have equal attributes.
     */
    @Override
    public boolean equals(Object aDate) {
        if (aDate == this) {
            return true;
        }
        
        if (aDate == null) {
            return false;
        }
        
        if (!(aDate instanceof Date)) {
            return false;
        }
        
        Date date = (Date) aDate;
        
        return Integer.compare(year, date.year) == 0
                && Integer.compare(month, date.month) == 0
                && Integer.compare(day, date.day) == 0;
    }
    
    /**
     * Overrides hashCode for a date.
     */
    @Override
    public int hashCode() {
        return 1;
    }
    
    /**
     * Overrides the toString method to fit the dates format. Adds leading zeros
     * to the day and month.
     */
    @Override
    public String toString() {
        String newDay = String.format("%02d", day);
        String newMonth = String.format("%02d", month);
        
        return year + Main.DATE_SEPARATOR + newMonth + Main.DATE_SEPARATOR + newDay;
    }

    /**
     * Overrides the compareTo method to compare two objects of the type date.
     * Checks if the date d comes before, at the same time or after this.
     */
    @Override
    public int compareTo(Date d) {
        int yearDiff = year - d.year;
        if (yearDiff == 0) {
            int monthDiff = month - d.month;
            if (monthDiff == 0) {
                return day - d.day;
            }
            return monthDiff;
        }
        return yearDiff;
    }
    
    /**
     * Getter for the day of the date.
     * @return The dates day.
     */
    public int getDay() {
        return this.day;
    }
    
    /**
     * Getter for the month of the date.
     * @return The dates month.
     */
    public int getMonth() {
        return this.month;
    }
    
    /**
     * Getter for the year of the date.
     * @return The dates year.
     */
    public int getYear() {
        return this.year;
    }
    
    /**
     * Getter for isLeapYear that returns its boolean value.
     * @return True if the year is a leap year.
     */
    public boolean getIsLeapYear() {
        return this.isLeapYear;
    }
}