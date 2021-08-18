package edu.kit.informatik.timeRecordingSystem.time;

import edu.kit.informatik.timeRecordingSystem.DateUtility;

/**
 * Models a break time for a work session. Breaks can be implemented
 * at any time during a shift. An employee is not allowed to work
 * more than six hours without taking a break. Shifts between six and
 * nine hours require at least a half an hour break. Shifts over nine
 * hours must have a break of at least 45 minutes. Modeled as an
 * immutable Object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class BreakTime {
    
    /**
     * The duration of a short break is 30 minutes.
     */
    private static final int SHORT_BREAK = 30;
    
    /**
     * The duration of a long break is 45 minutes.
     */
    private static final int LONG_BREAK = 45;
    
    /**
     * The time at which the break starts.
     */
    private final Time startBreak;
    
    /**
     * The time at which the break ends.
     */
    private final Time endBreak;
    
    /**
     * Constructor for a break. Initializes the start and end time
     * for the break. 
     * 
     * @param startBreak The starting time for the break.
     * @param endBreak The ending time for the break.
     */
    public BreakTime(Time startBreak, Time endBreak) {
        this.startBreak = startBreak;
        this.endBreak = endBreak;
    }
    
    /**
     * Calculates the duration (in minutes) of a break using the
     * duration method from the utility class for dates.
     * @return The length of the break in minutes.
     */
    public int breakDuration() {
        return DateUtility.duration(startBreak, endBreak);
    }
    
    /**
     * Calculates if the break is a short break or not. Short
     * breaks need to be at least 30 minutes long and implemented
     * in six to nine hour shifts.
     * @return True if the break is at least 30 minutes long.
     */
    public boolean isShortBreak() {
        if (breakDuration() >= SHORT_BREAK) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates if the break is a long break or not. Long
     * breaks need to be at least 45 minutes long and implemented
     * in over nine hour long shifts.
     * @return True if the break is at least 45 minutes long.
     */
    public boolean isLongBreak() {
        if (breakDuration() >= LONG_BREAK) {
            return true;
        }
        return false;
    }
    
    /**
     * Getter for the starting time of the break.
     * @return The time at which the break starts.
     */
    public Time getStartBreak() {
        return this.startBreak;
    }
    
    /**
     * Getter for the ending time of the break.
     * @return The time at which the break ends.
     */
    public Time getEndBreak() {
        return this.endBreak;
    }
}