package edu.kit.informatik.timeRecordingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Objects of the type WorkDays are used to store a players shifts in an ArrayList.
 * The class also contains various methods to interact with the ArrayList storing
 * the shifts.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class WorkDays implements Cloneable {
    
    /**
     * ArrayList of the type Shift used to store the employees shifts.
     */
    private ArrayList<Shift> workDays;
    
    /**
     * Constructor for WorkDays. Initializes the ArrayList that is used
     * to store an employees shifts.
     */
    public WorkDays() {
        workDays = new ArrayList<>();
    }
    
    /**
     * Adds a new shift to the ArrayList and then sorts it.
     * @param shift The shift that will be added.
     */
    public void add(Shift shift) {
        workDays.add(shift);
        sort();
    }
    
    /**
     * Creates and returns a copy of the current status of the shifts
     * ArrayList. The new ArrayList doesn't need to be sorted as its
     * elements are in the same order as in workDays.
     * @return An ArrayList of the type Shift that is a copy of the ArrayList
     * containing the employees shifts.
     */
    public ArrayList<Shift> copyOfWorkDays() {
        ArrayList<Shift> copy = new ArrayList<>();
        for (Shift s : workDays) {
            copy.add(s.clone());
        }
        return copy;
    }
    
    /**
     * Overrides clone() to create a deep copy of WorkDays. Has to iterate over
     * the ArrayList to create deep copies of the shifts instead of shallow copies.
     */
    @Override
    public WorkDays clone() {  
        try {
            WorkDays w = (WorkDays) super.clone();
            ArrayList<Shift> newArr = new ArrayList<>();
            for (Shift s : workDays) {
                newArr.add(s.clone());
            }
            w.workDays = newArr;
            return w;
        } catch (CloneNotSupportedException e) { 
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Sorts the ArrayList in ascending order of starting time for the shifts.
     */
    private void sort() {
        Collections.sort(workDays, new Comparator<Shift>() {
            
            /**
             * Overrides the compare method to compare two shifts
             */
            @Override
            public int compare(Shift wd1, Shift wd2) {
                return wd1.getWorkTime().getStartWork().compareTo(wd2.getWorkTime().getStartWork());
            }
        });
    }
}