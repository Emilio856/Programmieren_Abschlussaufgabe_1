package edu.kit.informatik.timeRecordingSystem.shifts;

import edu.kit.informatik.timeRecordingSystem.DateUtility;
import edu.kit.informatik.timeRecordingSystem.DeadlinesUtility;
import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Shift;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.WorkDays;
import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;

/**
 * Models a Sunday shift that extends a shift. Sunday shifts are work shifts
 * that occur during a Sunday. They can be performed during day or night time.
 * The daily work can be dragged on to ten hours if the average daily work doesn't
 * surpass eight hours in 6 months or 24 weeks. Employees must receive a rest day
 * within two weeks to compensate the shift.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class SundayShift extends Shift {
    
    /**
     * Minimal amount of free Sundays an employee needs to have in a year.
     */
    private static final int MIN_FREE_SUNDAYS = 15;
    
    /**
     * After working in a Sunday the employee has to receive a rest day in maximal two weeks.
     */
    private static final int WEEKS_TO_COMPENSATE = 2;
    
    /**
     * Most years have 52 Sundays. There are few exceptions for this rule.
     */
    private static final int YEAR_52_SUNDAYS = 52;
    
    /**
     * A year can sometimes have 53 Sundays in total.
     */
    private static final int YEAR_53_SUNDAYS = 53;
    
    /**
     * Error in case the Sunday shift is not compensated within 2 weeks.
     */
    private static final String COMPENSATE_DAY = "the work day has to be compensated in 2 weeks!";
    
    /**
     * Error in case an employee wouldn't be getting at least 15 free
     * Sundays in a year.
     */
    private static final String FREE_SUNDAYS = "an employee needs at least 15 free sundays every year!";
    
    /**
     * Defines if the work was performed at night or not.
     */
    private final boolean night;

    /**
     * Constructor for a Sunday shift with a break. Initializes
     * the attributes including work and break times.
     * @param workTime The time the employee spent at work.
     * @param breakTime The time the employee spent on a break.
     * @param night Defines if the work was performed at night.
     */
    public SundayShift(WorkTime workTime, BreakTime breakTime, boolean night) {
        super(workTime, breakTime);
        this.night = night;
    }
    
    /**
     * Constructor for a Sunday shift without a break. Initializes
     * the attributes including working time.
     * @param workTime The time the employee spent at work.
     * @param night Defines if the work was performed at night.
     */
    public SundayShift(WorkTime workTime, boolean night) {
        super(workTime);
        this.night = night;
    }

    /**
     * Overrides isValidShift from Shift. A Sunday shift is valid if the
     * maximal amount of daily work isn't surpassed, the maximal average work
     * is respected, the employee has his respective rest time, the work day
     * is compensated within two weeks and the employee has at least 15 free
     * Sundays each year.
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
                && ((restTimeWeeks(tempWorkDays, this)) || restTimeMonths(tempWorkDays, workDay))
                && compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)
                && fifteenFreeSundays(workDay, tempWorkDays)) {
            return true;
        } else if (night && DeadlinesUtility.workDuration(this) <= WorkDay.MAX_WORK
                && DeadlinesUtility.workDuration(this) >= WorkDay.MIN_WORK
                && (dailyWorkWeeks(Shift.AVERAGE_TWENTYFOUR_WEEKS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay)
                        || dailyWorkMonths(Shift.AVERAGE_SIX_MONTHS, Shift.AVERAGE_DAILY_WORK, tempWorkDays, workDay))
                && maxWork(tempWorkDays)
                && (restTimeWeeks(tempWorkDays, this)) || restTimeMonths(tempWorkDays, workDay)
                && compensateDay(WEEKS_TO_COMPENSATE, tempWorkDays, workDay, holidays)
                && fifteenFreeSundays(workDay, tempWorkDays)) {
            return true;
        }
        return false;
    }
    
    /**
     * Overrides inValidShift from Shift. Checks the reason why the shift is not
     * valid and returns an error explaining the reason. A Sunday shift can be
     * invalid if the maximal allowed working times are surpassed, the rest times
     * are not respected, the work day isn't compensated within two weeks or the
     * employee isn't getting at least 15 free Sundays each year.
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
                
            } else if (!fifteenFreeSundays(workDay, tempWorkDays)) {
                return Main.ERROR + FREE_SUNDAYS;
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
                
            } else if (!fifteenFreeSundays(workDay, tempWorkDays)) {
                return Main.ERROR + FREE_SUNDAYS;
            }
            return Main.ERROR + Shift.NOT_VALID_SHIFT;
        }
    }
    
    /**
     * Calculates if an employee has at least 15 free Sundays in a year after
     * adding a new work day. An employee has to get at least 15 free Sundays each year. Returns true
     * the condition is met.
     * @param workDay The work day to analyze.
     * @param workDays The recordings of the employees shifts.
     * @return True if the employee has at least 15 free Sundays each year.
     */
    private boolean fifteenFreeSundays(WorkDay workDay, WorkDays workDays) {
        int year = workDay.getWorkTime().getStartWork().getDate().getYear();
        Date date = new Date(DateUtility.FIRST_DAY, Main.JAN_NUM, year);
        
        while (DateUtility.dayOfWeek(date) != WorkDay.SUNDAY) {
            date = DateUtility.nextDay(date);
        }
        
        int sundays = numberOfSundays(workDay.getWorkTime().getStartWork().getDate());
        int sundaysWorked = 0;
        for (Shift wd : workDays.copyOfWorkDays()) {
            if (wd.getWorkTime().getStartWork().getDate().getYear() == year
                    && wd.isSunday()) {
                sundaysWorked++;
            }
        }
        return sundays - sundaysWorked >= MIN_FREE_SUNDAYS;
    }
    
    /**
     * Calculates the amount of Sundays a year has. Most years have 52 weeks and
     * therefore have 52 Sundays. A year can also have 53 Sundays if the year starts
     * on a Sunday or is a leap year and has a Sunday within the first two days
     * @param date The date of the working day that is used to calculate the amount
     * of Sundays in the corresponding year.
     * @return The amount of Sundays in the year of the date. The amount of Sundays
     * can be 52 or 53 depending on the year.
     */
    private int numberOfSundays(Date date) {
        Date firstDay = new Date(DateUtility.FIRST_DAY, Main.JAN_NUM, date.getYear());
        Date secondDay = new Date(2, Main.JAN_NUM, date.getYear());
        if (DateUtility.dayOfWeek(firstDay) == WorkDay.SUNDAY) {
            return YEAR_53_SUNDAYS;
            
        } else if (date.getIsLeapYear()
                && (DateUtility.dayOfWeek(firstDay) == WorkDay.SUNDAY
                || DateUtility.dayOfWeek(secondDay) == WorkDay.SUNDAY)) {
            return YEAR_53_SUNDAYS;
        } else {
            return YEAR_52_SUNDAYS;
        }
    }
}