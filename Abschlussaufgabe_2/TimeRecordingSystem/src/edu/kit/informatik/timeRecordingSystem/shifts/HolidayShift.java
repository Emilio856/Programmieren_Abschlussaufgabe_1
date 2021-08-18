package edu.kit.informatik.timeRecordingSystem.shifts;

import edu.kit.informatik.timeRecordingSystem.DeadlinesUtility;
import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Shift;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.WorkDays;
import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;

/**
 * Constructor for a shift on holidays that extends a shift. Holiday shifts are
 * work shifts that occur during the holidays including Sundays. They can be
 * performed during day or night time. The daily work can be dragged on to ten
 * hours if the daily work doesn't surpass eight hours in 6 months or 24 weeks.
 * Employees must receive a rest day within eight weeks to compensate the shift.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class HolidayShift extends Shift {
    
    /**
     * Error in case the rest day isn't compensated within eight weeks.
     */
    private static final String COMPENSATE_DAY = "the work day has to be compensated within 8 weeks!";
    
    /**
     * After working in a holiday the employee has to receive a rest day in maximal eight weeks.
     */
    private static final int WEEKS_TO_COMPENSATE = 8;
    
    /**
     * Defines if the work was performed at night or not.
     */
    private final boolean night;

    /**
     * Constructor for a holiday shift with a break. Initializes
     * the attributes including working and break times.
     * @param workTime The time the employee spent at work.
     * @param breakTime The time the employee spent on a break.
     * @param night Defines if the work was performed at night.
     */
    public HolidayShift(WorkTime workTime, BreakTime breakTime, boolean night) {
        super(workTime, breakTime);
        this.night = night;
    }
    
    /**
     * Constructor for a holiday shift without a break. Initializes
     * the attributes including working time.
     * @param workTime The time the employee spent at work.
     * @param night Defines if the work was performed at night.
     */
    public HolidayShift(WorkTime workTime, boolean night) {
        super(workTime);
        this.night = night;
    }

    /**
     * Overrides isValidShift from Shift. A holiday shift is valid if the
     * maximal amount of daily work isn't surpassed, the maximal average work
     * is respected, the employee has his respective rest time and the work
     * day is compensated within eight weeks.
     */
    @Override
    public boolean isValidShift(WorkDay workDay, WorkDays workDays, Holidays holidays) {
        WorkDays tempWorkDays = workDays.clone();
        tempWorkDays.add(this);
        
        if (!night && DeadlinesUtility.workDuration(this) <= WorkDay.MAX_WORK
                && DeadlinesUtility.workDuration(this) >= WorkDay.MIN_WORK
                && (dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && maxWork(tempWorkDays)
                && (restTimeWeeks(tempWorkDays, this) || restTimeMonths(tempWorkDays, workDay))
                && compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)) {
            return true;
        } else if (night && DeadlinesUtility.workDuration(this) <= WorkDay.MAX_WORK
                && DeadlinesUtility.workDuration(this) >= WorkDay.MIN_WORK
                && (dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && maxWork(tempWorkDays)
                && (restTimeWeeks(tempWorkDays, this) || restTimeMonths(tempWorkDays, workDay))
                && compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)) {
            return true;
        }
        return false;
    }

    /**
     * Overrides inValidShift from Shift. Checks the reason why the shift is not
     * valid and returns an error explaining the reason. A holiday shift can be
     * invalid if the maximal allowed working times are surpassed, the rest times
     * are not respected or the work day isn't compensated within eight weeks.
     */
    @Override
    public String invalidShift(WorkDay workDay, WorkDays workDays, Holidays holidays) {
        WorkDays tempWorkDays = workDays.clone();
        tempWorkDays.add(this);
        
        if (!night) {
            if (DeadlinesUtility.workDuration(this) > WorkDay.MAX_WORK || !maxWork(tempWorkDays)) {
                return Main.ERROR + Shift.MAX_DAILY_WORK;
                
            } else if (DeadlinesUtility.workDuration(this) < WorkDay.MIN_WORK) {
                return Main.ERROR + Shift.MIN_DAILY_WORK;
                
            } else if ((!dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS,
                    Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                    || !dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))) {
                return Main.ERROR + Shift.AVERAGE_WORK;
                
            } else if ((!restTimeWeeks(tempWorkDays, this) || !restTimeMonths(tempWorkDays, workDay))) {
                return Main.ERROR + Shift.REST_TIME;
                
            } else if (!compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)) {
                return Main.ERROR + COMPENSATE_DAY;
            }
            return Main.ERROR + Shift.NOT_VALID_SHIFT;
            
        } else {
            if (DeadlinesUtility.workDuration(this) > WorkDay.MAX_WORK || !maxWork(tempWorkDays)) {
                return Main.ERROR + Shift.MAX_DAILY_WORK;
                
            } else if (DeadlinesUtility.workDuration(this) < WorkDay.MIN_WORK) {
                return Main.ERROR + Shift.MIN_DAILY_WORK;
                
            } else if ((!dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS,
                    Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                    || !dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))) {
                return Main.ERROR + Shift.AVERAGE_WORK;
                
            } else if ((!restTimeWeeks(tempWorkDays, this) || !restTimeMonths(tempWorkDays, workDay))) {
                return Main.ERROR + Shift.REST_TIME;
                
            } else if (!compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)) {
                return Main.ERROR + COMPENSATE_DAY;
            }
            return Main.ERROR + Shift.NOT_VALID_SHIFT;
        }
    }
}
