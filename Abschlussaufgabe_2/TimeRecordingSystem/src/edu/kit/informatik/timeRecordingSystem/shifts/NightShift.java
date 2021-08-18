package edu.kit.informatik.timeRecordingSystem.shifts;

import edu.kit.informatik.timeRecordingSystem.DeadlinesUtility;
import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Shift;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.WorkDays;
import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.Hour;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;

/**
 * Models a night shift that extends a shift. Night shifts are work shifts that
 * occur during the night time. The daily work can be dragged on to ten hours if
 * the average daily work doesn't surpass eight hours in one month or 4 weeks.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class NightShift extends Shift {
    
    /**
     * Error message in case an unauthorized employee tries to work during night time.
     */
    public static final String NO_NIGHT_SHIFT = "night work can only be performed by night workers!";
    /**
     * The hour at which the night time starts.
     */
    public static final int START_NIGHT = 23;
    
    /**
     * The hour at which the night time ends.
     */
    public static final int END_NIGHT = 6;
    
    /**
     * The minute at which an hour can start or end.
     */
    public static final int START_END_HOUR = 0;
    
    /**
     * The minimum amount of minutes an employee needs to work during the
     * night to accomplish a night shift.
     */
    public static final int MIN_LENGTH = 120;
    
    /**
     * The hour (23:00) at which the night time starts.
     */
    public static final Hour START_NIGHT_SHIFT = new Hour(START_NIGHT, START_END_HOUR);
    
    /**
     * The hour (06:00) at which the night night time ends.
     */
    public static final Hour END_NIGHT_SHIFT = new Hour(END_NIGHT, START_END_HOUR);
    
    /**
     * Constructor for a night shift with a break.
     * @param workTime The time the employee spent at work.
     * @param breakTime The time the employee spent on a break.
     */
    public NightShift(WorkTime workTime, BreakTime breakTime) {
        super(workTime, breakTime);
    }
    
    /**
     * Constructor for a night shift without a break.
     * @param workTime The time the employee spent at work.
     */
    public NightShift(WorkTime workTime) {
        super(workTime);
    }

    /**
     * Overrides isValidShift from Shift. A night shift is valid if the maximal
     * amount of daily work isn't surpassed, the maximal average work is respected
     * and the employee has his respective rest time. Night shifts have to cover
     * at least two hours of the night time.
     */
    @Override
    public boolean isValidShift(WorkDay workDay, WorkDays workDays, Holidays holidays) {
        WorkDays tempWorkDays = workDays.clone();
        tempWorkDays.add(this);
        
        if (DeadlinesUtility.workDuration(this) <= WorkDay.MAX_WORK
                && DeadlinesUtility.workDuration(this) >= WorkDay.MIN_WORK
                && (dailyWorkWeeks(Shift.AVERAGE_FOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_ONE_MONTH, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && (dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && maxWork(tempWorkDays)
                && (restTimeWeeks(tempWorkDays, this)) || restTimeMonths(tempWorkDays, workDay)) {
            return true;
        }
        return false;
    }

    /**
     * Overrides inValidShift from Shift. Checks the reason why the shift is not
     * valid and returns an error explaining the reason. A night shift can be
     * invalid if the maximal allowed working times are surpassed or the rest
     * times are not respected.
     */
    @Override
    public String invalidShift(WorkDay workDay, WorkDays workDays, Holidays holidays) {
        WorkDays tempWorkDays = workDays.clone();
        tempWorkDays.add(this);
        
        if (DeadlinesUtility.workDuration(this) > WorkDay.MAX_WORK || !maxWork(tempWorkDays)) {
            return Main.ERROR + Shift.MAX_DAILY_WORK;
            
        } else if (DeadlinesUtility.workDuration(this) < WorkDay.MIN_WORK) {
            return Main.ERROR + Shift.MIN_DAILY_WORK;
            
        } else if ((!dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                || !dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))) {
            return Main.ERROR + Shift.AVERAGE_WORK;
            
        } else if ((!dailyWorkWeeks(Shift.AVERAGE_FOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                || !dailyWorkMonths(Shift.AVERAGE_ONE_MONTH, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))) {
            return Main.ERROR + Shift.AVERAGE_WORK;
            
        } else if ((!restTimeWeeks(tempWorkDays, this)) || !restTimeMonths(tempWorkDays, workDay)) {
            return Main.ERROR + Shift.REST_TIME;
        }
        return Main.ERROR + Shift.NOT_VALID_SHIFT;
    }
}