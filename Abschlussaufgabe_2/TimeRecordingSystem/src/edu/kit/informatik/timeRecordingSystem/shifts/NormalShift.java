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
 * Models a normal shift that extends a shift. Normal shifts are work shifts
 * that occur during the daytime and don't include Sunday oder Holidays. The
 * daily work can be dragged on to ten hours if the average daily work doesn't
 * surpass eight hours in 6 months or 24 weeks.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class NormalShift extends Shift {

    /**
     * Constructor for a normal shift with a break. 
     * @param workTime The time the employee spent at work.
     * @param breakTime The time the employee spent on a break.
     */
    public NormalShift(WorkTime workTime, BreakTime breakTime) {
        super(workTime, breakTime);
    }
    
    /**
     * Constructor for a normal shift without a break.
     * @param workTime The time the employee spent at work.
     */
    public NormalShift(WorkTime workTime) {
        super(workTime);
    }
    
    /**
     * Overrides isValidShift from Shift. A normal shift is valid if the maximal
     * amount of daily work isn't surpassed, the maximal average work is respected
     * and the employee has his respective rest time.
     */
    @Override
    public boolean isValidShift(WorkDay workDay, WorkDays workDays, Holidays holidays) {
        WorkDays tempWorkDays = workDays.clone();
        tempWorkDays.add(this);
        
        if (DeadlinesUtility.workDuration(this) <= WorkDay.MAX_WORK
                && DeadlinesUtility.workDuration(this) >= WorkDay.MIN_WORK
                && (dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && maxWork(tempWorkDays)
                && (restTimeWeeks(tempWorkDays, this) || restTimeMonths(tempWorkDays, workDay))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overrides invalidShift from Shift. Checks the reason why the shift is not
     * valid and returns an error explaining the reason. A normal shift can be
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
            
        } else if ((!restTimeWeeks(tempWorkDays, this) || !restTimeMonths(tempWorkDays, workDay))) {
            return Main.ERROR + Shift.REST_TIME;
        }
        return Main.ERROR + Shift.NOT_VALID_SHIFT;
    }
}