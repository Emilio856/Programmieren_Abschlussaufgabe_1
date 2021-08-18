package edu.kit.informatik.timeRecordingSystem.typesOfEmployees;

import java.util.ArrayList;

import edu.kit.informatik.timeRecordingSystem.DateUtility;
import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Name;
import edu.kit.informatik.timeRecordingSystem.Shift;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.WorkDays;
import edu.kit.informatik.timeRecordingSystem.shifts.HolidayShift;
import edu.kit.informatik.timeRecordingSystem.shifts.NightShift;
import edu.kit.informatik.timeRecordingSystem.shifts.NormalShift;
import edu.kit.informatik.timeRecordingSystem.shifts.SundayShift;
import edu.kit.informatik.timeRecordingSystem.time.Date;

/**
 * Models an employee of a company in an abstract class. An employee has a name
 * consisting of first and last name, a date of birth, a type and an identification
 * number. An Employee can record work shifts in an object of the type WorkDays.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public abstract class Employee implements Cloneable {
    
    /**
     * A counter to increase the identification number after
     * creating a new Employee.
     */
    private static int idCounter = 0;
    /**
     * Decides if the employee is allowed to work everyday.
     */
    protected boolean canWorkEveryday;
    /**
     * Decides if the employee is allowed to work at night time.
     */
    protected boolean canNightShift;
    /**
     * The employees name consisting of first and last name.
     */
    private final Name name;
    /**
     * The employees date of birth.
     */
    private final Date birthday;
    /**
     * The employees holidays.
     */
    private final Holidays holidays;
    /**
     * Defines the employees type (regular, night, production or night production)
     */
    private final String type;
    /**
     * The unique identification number of the employee
     */
    private final int identification;
    /**
     * Saves the recorded work from the employee.
     */
    private WorkDays workDays;
    
    /**
     * Constructor for an employee. Initializes the name, date of birth, holidays
     * and type. Also sets the new identification number and increments it.
     * 
     * @param name The employees name.
     * @param birthday The date of birth of the employee.
     * @param holidays The employees holidays.
     * @param type The employees type (regular, night, production and night production)
     */
    public Employee(Name name, Date birthday, Holidays holidays, String type) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
        this.holidays = holidays;
        ++idCounter;
        this.identification = idCounter;
        this.workDays = new WorkDays();
    }
    
    /**
     * Checks if an employee is allowed to work the planned work day based on
     * holiday work, night work and Sunday work criteria.
     * 
     * @param workDay The planned work day to be analyzed.
     * @return True if all day and time requirements are fulfilled.
     */
    public abstract boolean canWork(WorkDay workDay);
    
    /**
     * Checks the same criteria as canWork(WorkDay) and returns an error message explaining
     * the error depending on holiday work, night work or Sunday work.
     * @param workDay The planned day to be analyzed.
     * @return An error message explaining why the planned work day cannot be recorded by
     * the particular employee.
     */
    public abstract String reasonCantWork(WorkDay workDay);
    
    /**
     * Registers work for the employee. Depending on the planned work day a shift is created
     * and analyzed for correctness. If the new shift is valid it is added to the employee,
     * else a corresponding error is returned.
     * @param workDay The planned work day.
     * @return A message with the works duration if the shift is valid. Else an error message
     * explaining the failure is returned.
     */
    public String work(WorkDay workDay) {
        Shift shift;
        if (workDay.getHasBreak()) {
            if (!workDay.isSunday() && !workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new NormalShift(workDay.getWorkTime(), workDay.getBreakTime());
                
            } else if (!workDay.isSunday() && !workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new NightShift(workDay.getWorkTime(), workDay.getBreakTime());
                
            } else if (workDay.isSunday() && !workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new SundayShift(workDay.getWorkTime(), workDay.getBreakTime(), false);
                
            } else if (workDay.isSunday() && !workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new SundayShift(workDay.getWorkTime(), workDay.getBreakTime(), true);
                
            } else if (workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new HolidayShift(workDay.getWorkTime(), workDay.getBreakTime(), false);
                
            } else if (workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new HolidayShift(workDay.getWorkTime(), workDay.getBreakTime(), true);
            } else {
                shift = new NormalShift(workDay.getWorkTime(), workDay.getBreakTime());
            }
        } else {
            if (!workDay.isSunday() && !workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new NormalShift(workDay.getWorkTime());
                
            } else if (!workDay.isSunday() && !workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new NightShift(workDay.getWorkTime());
                
            } else if (workDay.isSunday() && !workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new SundayShift(workDay.getWorkTime(), false);
                
            } else if (workDay.isSunday() && !workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new SundayShift(workDay.getWorkTime(), true);
                
            } else if (workDay.isHoliday(getHolidays()) && !workDay.isNightShift()) {
                shift = new HolidayShift(workDay.getWorkTime(), false);
                
            } else if (workDay.isHoliday(getHolidays()) && workDay.isNightShift()) {
                shift = new HolidayShift(workDay.getWorkTime(), true);
            } else {
                shift = new NormalShift(workDay.getWorkTime());
            }
        }
        
        if (shift.isValidShift(workDay, workDays, holidays)) {
            
            workDays.add(shift);
            return DateUtility.formatDuration(workDay).toString();
        } else {
            return shift.invalidShift(workDay, workDays, holidays);
        }
    }
    
    /**
     * Overrides clone() to create a deep copy of the employee.
     * Catches CloneNutSupportedException and prints an stack trace
     * for the exception.
     */
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Overrides toString() to return a String representation of an
     * employee. This representation consists of the players first and
     * last name, the date of birth and employee type.
     */
    @Override
    public String toString() {
        return this.name.toString() + Main.SPACE_SEPARATOR + birthday.toString() + Main.SPACE_SEPARATOR + this.type;
    }
    
    /**
     * Creates a copy of the ArrayList containing the recorded work.
     * @return An ArrayList of the type Shift with the recorded work
     * by the employee.
     */
    public ArrayList<Shift> copyOfWorkDays() {
        return workDays.copyOfWorkDays();
    }
    
    /**
     * Getter for a clone of workDays containing the recorded work by
     * the employee. Encapsulation rules are respected as this is
     * only a deep copy of the object and not the object itself.
     * @return A deep copy of the employees shifts.
     */
    public WorkDays getWorkDays() {
        return this.workDays.clone();
    }
    
    /**
     * Getter for the employees name that contains the first and
     * last name.
     * @return The employees name.
     */
    public Name getName() {
        return this.name;
    }
    
    /**
     * Getter for the employees date of birth of the type Date.
     * @return The employees date of birth.
     */
    public Date getBirthday() {
        return this.birthday;
    }
    
    /**
     * Getter for the employees unique identification number.
     * @return The players identification.
     */
    public int getIdentification() {
        return this.identification;
    }
    
    /**
     * Getter for the dates that count as holidays.
     * @return The employees Holidays.
     */
    public Holidays getHolidays() {
        return this.holidays;
    }
}