package edu.kit.informatik.timeRecordingSystem;

import java.util.ArrayList;

import edu.kit.informatik.timeRecordingSystem.time.Date;

/**
 * Models holidays. The program opens a file at the beginning containing
 * the holidays dates. If the file contains valid dates they saved as
 * holidays. If an error occurs or the syntax in the file is incorrect
 * the data will no longer be used. Modeled as an immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Holidays {
    
    /**
     * ArrayList of the type Date containing the holidays.
     */
    private final ArrayList<Date> holidays;
    
    /**
     * Defines if the holidays are valid or not.
     */
    private final boolean validHolidays;
    
    /**
     * Receives a String array as parameter and analyzes the data to check
     * if they are all valid dates. If the dates are not valid validHolidays
     * is set to false and the ArrayList to null. 
     * 
     * If the data is correct the ArrayList is initiated with all the dates
     * from the file.
     * 
     * @param holidays The String array containing the files data.
     */
    public Holidays(String[] holidays) {
        validHolidays = validHolidays(holidays);
        
        if (validHolidays) {
            this.holidays = initHolidays(holidays);
        } else {
            this.holidays = null;
        }
    }
    
    /**
     * Checks if a specific date is part of the holidays or not.
     * @param date The date that might be or not be part of the holidays.
     * @return True if the holidays are valid and can therefore be used in the program.
     */
    public boolean isHoliday(Date date) {
        if (!validHolidays) {
            return false;
        }
        for (Date d : holidays) {
            if (d.equals(date)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if an array of strings contains valid dates and
     * returns the result.
     * @param holidays The array of string that will be analyzed.
     * @return True if the array contains only valid dates. False if
     * at least one date is incorrect or non existent.
     */
    private boolean validHolidays(String[] holidays) {
        
        for (String s : holidays) {
            if (!DateUtility.validDate(s)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Initializes the holidays ArrayList containing the dates. Puts
     * all the values from an array into the ArrayList and returns it.
     * @param strHolidays An array of strings containing the dates
     * for holidays.
     * @return An ArrayList of Date with the holidays dates.
     */
    private ArrayList<Date> initHolidays(String[] strHolidays) {
        ArrayList<Date> holidays = new ArrayList<>();
        
        for (String s : strHolidays) {
            holidays.add(DateUtility.separateDate(s));
        }
        return holidays;
    }
    
    /**
     * Getter for the array containing the information about the holidays. String[] holidays
     * is final and this method is within the guidelines for data encapsulation.
     * @return The array containing the information about the holidays.
     */
    public ArrayList<Date> getHolidays() {
        return this.holidays;
    }
    
    /**
     * Getter for validHolidays. If the given holidays are valid true is returned.
     * @return The value of validHolidays.
     */
    public boolean getValidHolidays() {
        return this.validHolidays;
    }
    
}