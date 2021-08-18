package edu.kit.informatik;

/**
 * Models an date
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Date {
    //Attributes
    private int day;
    private int month;
    private int year;
    
    /**
     * Constructor for a date
     * @param day The day of the date
     * @param month The month of the date
     * @param year The year of the date
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    //It is very important to always know the exact date for each flight
    public int getDay() {
        return this.day;
    }
    
    //It is very important to always know the exact date for each flight
    public int getMonth() {
        return this.month;
    }
    
    //It is very important to always know the exact date for each flight
    public int getYear() {
        return this.year;
    }
    
    //Conventional form to display a date
    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }
}