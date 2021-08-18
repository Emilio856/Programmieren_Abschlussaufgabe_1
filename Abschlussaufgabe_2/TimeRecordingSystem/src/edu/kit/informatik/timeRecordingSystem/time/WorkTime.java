package edu.kit.informatik.timeRecordingSystem.time;

import edu.kit.informatik.timeRecordingSystem.DateUtility;

/**
 * Models a work time for a work session. A work time can start and end
 * at any time, only depending on the employees limitations. Shifts of
 * between six and nine hours require a break of at least 30 minutes. If
 * the shift exceeds nine hours the break has to be at least 45 minutes
 * long. Modeled as an immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class WorkTime {
    
    /**
     * The lower length time (in minutes) bound for requiring a short break.
     * Work times below this length don't require a break.
     */
    private static final int SHORT_BREAK_LOWER_BOUND = 360;
    
    /**
     * The upper length time (in minutes) bound for requiring a short break.
     * Work times over this length require a long break of at least 45 minutes.
     */
    private static final int SHORT_BREAK_UPPER_BOUND = 540;
    
    /**
     * The time at which the work starts.
     */
    private final Time startWork;
    
    /**
     * The time at which the work ends.
     */
    private final Time endWork;
    
    /**
     * Defines if the work time requires a short break to fulfill
     * the legal requirements.
     */
    private final boolean needsShortBreak;
    
    /**
     * Defines if the work time requires a long break to fulfill
     * the legal requirements.
     */
    private final boolean needsLongBreak;
    
    /**
     * Constructor for a work time. Initializes the start and end
     * times for the working time and sets the values for
     * needsShortBreak and needsLongBreak. needsShortBreak and
     * needsLongBreak can't be true at the same time.
     * 
     * @param workStart The starting time for the work.
     * @param workEnd The ending time for the work.
     */
    public WorkTime(Time workStart, Time workEnd) {
        this.startWork = workStart;
        this.endWork = workEnd;
        this.needsShortBreak = needsShortBreak();
        this.needsLongBreak = needsLongBreak();
    }
    
    /**
     * Calculates the duration (in minutes) of a break using the
     * duration method from the utility class for dates.
     * @return The length of the work excluding break time.
     */
    public int workDuration() {
        return DateUtility.duration(startWork, endWork);
    }
    
    /**
     * Calculates if the work session requires a short 30 minutes
     * break. A short break is required when the employee works
     * six to nine hours.
     * @return True if the employees shift needs to contain a
     * short break.
     */
    private boolean needsShortBreak() {
        if (workDuration() >= SHORT_BREAK_LOWER_BOUND && workDuration() <= SHORT_BREAK_UPPER_BOUND) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates if the work session requires a long 45 minutes
     * break. A long break is required when the employee works
     * over nine hours.
     * @return True if the employees shift needs to contain a
     * long break.
     */
    private boolean needsLongBreak() {
        if (workDuration() > SHORT_BREAK_UPPER_BOUND) {
            return true;
        }
        return false;
    }
    
    /**
     * Getter for the starting time of the work.
     * @return The time at which the work starts.
     */
    public Time getStartWork() {
        return this.startWork;
    }
    
    /**
     * Getter for the ending time of the work.
     * @return The time at which the work ends.
     */
    public Time getEndWork() {
        return this.endWork;
    }
    
    /**
     * Getter for needsShortBreak. Shows if the working time
     * requires a short break.
     * @return True if a short break is required.
     */
    public boolean getNeedsShortBreak() {
        return this.needsShortBreak;
    }
    
    /**
     * Getter for needsLongBreak. Shows if the working time
     * requires a long break.
     * @return True if a long break is required.
     */
    public boolean getNeedsLongBreak() {
        return this.needsLongBreak;
    }
    
}