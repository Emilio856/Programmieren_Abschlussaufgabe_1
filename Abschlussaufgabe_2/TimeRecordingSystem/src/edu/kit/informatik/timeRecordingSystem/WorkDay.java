package edu.kit.informatik.timeRecordingSystem;

import edu.kit.informatik.timeRecordingSystem.shifts.NightShift;
import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Hour;
import edu.kit.informatik.timeRecordingSystem.time.Time;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;

/**
 * Models a planned work day for an employee. a work day can contain
 * one break or none. An employee is not allowed to work more than six
 * consecutive hours without taking a break.
 * 
 * This class is also able to determine if the introduced break is valid
 * and if the work occurs during night, Sunday or holidays. Modeled as
 * immutable object.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class WorkDay {
    
    /**
     * The hour at which the rest time is completed after a rest day.
     */
    public static final Hour UPPER_BOUND_REST_TIME = new Hour(11, 0);
    /**
     * The hour at which the rest time is completed before a rest day.
     */
    public static final Hour LOWER_BOUND_REST_TIME = new Hour(13, 0);
    /**
     * The hour at which a day ends.
     */
    public static final Hour END_DAY = new Hour(24, 0);
    /**
     * The hour at which a day starts.
     */
    public static final Hour START_DAY = new Hour(0, 0);
    /**
     * An employee has to work for at least one minute during a shift.
     */
    public static final int MIN_WORK = 1;
    /**
     * The maximal amount of minutes an employee is allowed to work consecutively or in a day.
     */
    public static final int MAX_WORK = 600;
    /**
     * 11 hours rest time in minutes.
     */
    public static final int REST_TIME = 660;
    /**
     * Minimal rest time an employee needs.
     */
    public static final int MIN_REST_TIME = 600;
    /**
     * The number for a Sunday in a week day calculation system.
     */
    public static final int SUNDAY = 0;
    /**
     * The number for a Monday in a week day calculation system.
     */
    protected static final int MONDAY = 1;
    /**
     * The number for a Tuesday in a week day calculation system.
     */
    protected static final int TUESDAY = 2;
    /**
     * The number for a Wednesday in a week day calculation system.
     */
    protected static final int WEDNESDAY = 3;
    /**
     * The number for a Thursday in a week day calculation system.
     */
    protected static final int THURSDAY = 4;
    /**
     * The number for a Friday in a week day calculation system.
     */
    protected static final int FRIDAY = 5;
    /**
     * The number for a Saturday in a week day calculation system.
     */
    protected static final int SATURDAY = 6;
    /**
     * An employee can only work up to 360 minutes without taking a break.
     */
    private static final int MAX_WORK_INTERVALL = 360;
    
    /**
     * The work time for the work day. Contains start and end time
     * for the work.
     */
    private final WorkTime workTime;
    
    /**
     * The break time for the work day. Contains start and end time
     * for the break.
     */
    private final BreakTime breakTime;
    
    /**
     * Defines if the break is valid or not.
     */
    private final boolean validBreak;
    
    /**
     * Defines if the work contains a break.
     */
    private final boolean hasBreak;
    
    /**
     * Defines if the chronological order of the times is correct.
     */
    private final boolean validTimes;
    
    /**
     * Constructor for a work day containing a break. Analyzes
     * if the given break is valid or not.
     * @param workTime The work interval that also contains
     * the break. 
     * @param breakTime The break interval inside workTime.
     */
    public WorkDay(WorkTime workTime, BreakTime breakTime) {
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.hasBreak = true;
        this.validBreak = isValidBreak();
        this.validTimes = isValidTime();
    }
    
    /**
     * Constructor for a work day without a break. Sets the
     * attributes corresponding to the break to false as there
     * is no brake in this case.
     * @param workTime The work interval
     */
    public WorkDay(WorkTime workTime) {
        this.workTime = workTime;
        this.breakTime = null;
        this.hasBreak = false;
        this.validBreak = false;
        this.validTimes = isValidTime();
    }
    
    /**
     * Checks if the work day is covers the requirements for a night shift.
     * A shift is a night shift when at least two hours are work during the
     * night time (23:00 to 06:00). Breaks to not count as work during night
     * time.
     * 
     * @return True if at least two hours are worked during the night time.
     */
    public boolean isNightShift() {
        int workDuration = nightShiftLength() - nightBreakLength();
        
        if (workDuration >= NightShift.MIN_LENGTH) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the work day starts or ends on a Sunday and therefore counts
     * as Sunday-work. Breaks do not count as working on a Sunday.
     * @return True if work is performed on a Sunday.
     */
    public boolean isSunday() {
        if (hasBreak) {
            if (this.getWorkTime().getStartWork().equals(this.getBreakTime().getStartBreak())
                    && this.getBreakTime().getEndBreak().getHour().equals(END_DAY)) {
                return DateUtility.dayOfWeek(this.getWorkTime().getEndWork().getDate()) == SUNDAY;
                
            } else if (this.getBreakTime().getEndBreak().equals(this.workTime.getEndWork())
                    && this.getBreakTime().getStartBreak().getHour().equals(START_DAY)) {
                return DateUtility.dayOfWeek(this.getWorkTime().getStartWork().getDate()) == SUNDAY;
            }
            return DateUtility.dayOfWeek(this.getWorkTime().getStartWork().getDate()) == SUNDAY
                    || DateUtility.dayOfWeek(this.getWorkTime().getEndWork().getDate()) == SUNDAY;
            
        } else {
            return DateUtility.dayOfWeek(this.getWorkTime().getStartWork().getDate()) == SUNDAY
                    || DateUtility.dayOfWeek(this.getWorkTime().getEndWork().getDate()) == SUNDAY;
        }
    }
    
    /**
     * Checks if the work day starts or ends on a holiday and therefore counts as
     * holiday-work. Breaks do not count as working on a holiday.
     * @param holidays The date that count as holidays.
     * @return True if work is performed on a holiday.
     */
    public boolean isHoliday(Holidays holidays) {
        if (holidays != null && holidays.getValidHolidays()) {
            for (Date date : holidays.getHolidays()) {
                if (date.equals(this.getWorkTime().getStartWork().getDate())
                        || date.equals(this.getWorkTime().getEndWork().getDate())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Auxiliary method to calculate the length of the night work. Handles cases
     * in which the work starts and ends in the same day or different days. Start
     * and end time can be part of night time or not.
     * @return The working and break time during night time in minutes.
     */
    private int nightShiftLength() {
        int nightShiftLength = 0;
        
        //Starts and ends on different days. The starting time doesn't belong to the night time.
        if (this.getWorkTime().getStartWork().getDate().getDay()
                != this.getWorkTime().getEndWork().getDate().getDay()
                && this.getWorkTime().getStartWork().getHour().getHour() < NightShift.START_NIGHT) {
            
            Time tempTime1 = new Time(this.getWorkTime().getStartWork().getDate(), NightShift.START_NIGHT_SHIFT);
            if (this.getWorkTime().getEndWork().getHour().getHour() < NightShift.END_NIGHT) {
                nightShiftLength = DateUtility.duration(tempTime1, this.getWorkTime().getEndWork());
            } else {
                Time tempTime2 = new Time(this.getWorkTime().getEndWork().getDate(), NightShift.END_NIGHT_SHIFT);
                nightShiftLength = DateUtility.duration(tempTime1, tempTime2);
            }
            
        //Starts and ends on different days. The starting time belongs to the night time.
        } else if (this.getWorkTime().getStartWork().getDate().getDay()
                != this.getWorkTime().getEndWork().getDate().getDay()
                && this.getWorkTime().getStartWork().getHour().getHour() >= NightShift.START_NIGHT) {
            
            if (this.getWorkTime().getEndWork().getHour().getHour() < NightShift.END_NIGHT) {
                nightShiftLength = DateUtility.duration(this.getWorkTime().getStartWork(),
                        this.getWorkTime().getEndWork());
            } else {
                Time tempTime = new Time(this.getWorkTime().getEndWork().getDate(), NightShift.END_NIGHT_SHIFT);
                nightShiftLength = DateUtility.duration(this.getWorkTime().getStartWork(), tempTime);
            }
            
        //Starts and ends on the same day. The entire work is part of night time.
        } else if (this.getWorkTime().getStartWork().getDate().getDay()
                == this.getWorkTime().getEndWork().getDate().getDay()
                && this.getWorkTime().getStartWork().getHour().getHour() < NightShift.END_NIGHT
                && this.getWorkTime().getEndWork().getHour().getHour() < NightShift.END_NIGHT) {
            
            nightShiftLength = DateUtility.duration(this.getWorkTime().getStartWork(),
                    this.getWorkTime().getEndWork());
            
        //Starts and ends on the same day. Start time is part of night time, but end time not.
        } else if (this.getWorkTime().getStartWork().getDate().getDay()
                == this.getWorkTime().getEndWork().getDate().getDay()
                && this.getWorkTime().getStartWork().getHour().getHour() < NightShift.END_NIGHT
                && this.getWorkTime().getEndWork().getHour().getHour() >= NightShift.END_NIGHT) {
            
            Time tempTime = new Time(this.getWorkTime().getEndWork().getDate(), NightShift.END_NIGHT_SHIFT);
            nightShiftLength = DateUtility.duration(this.getWorkTime().getStartWork(), tempTime);
        }
        return nightShiftLength;
    }
    
    /**
     * Auxiliary method to calculate the breaks length during night time.
     * Handles cases in which the work starts and ends in the same day or
     * different days. Start and end time can be part of night time or not.
     * @return The duration of the break during the night time in minutes.
     */
    private int nightBreakLength() {
        int breakLength = 0;
        
        if (!this.getHasBreak()) {
            breakLength = 0;
        } else {
            
            //Starts and ends on different days. The starting time doesn't belong to the night time.
            if (this.getBreakTime().getStartBreak().getDate().getDay()
                != this.getBreakTime().getEndBreak().getDate().getDay()
                && this.getBreakTime().getStartBreak().getHour().getHour() < NightShift.START_NIGHT) {
                
                Time tempTime1 = new Time(this.getBreakTime().getStartBreak().getDate(),
                        NightShift.START_NIGHT_SHIFT);
                if (this.getBreakTime().getEndBreak().getHour().getHour() < NightShift.END_NIGHT) {
                    breakLength = DateUtility.duration(tempTime1, this.getBreakTime().getEndBreak());
                } else {
                    Time tempTime2 = new Time(this.getBreakTime().getEndBreak().getDate(),
                            NightShift.END_NIGHT_SHIFT);
                    breakLength = DateUtility.duration(tempTime1, tempTime2);
                }
                
            //Starts and ends on different days. The starting time belongs to the night time.
            } else if (this.getBreakTime().getStartBreak().getDate().getDay()
                != this.getBreakTime().getEndBreak().getDate().getDay()
                && this.getBreakTime().getStartBreak().getHour().getHour() >= NightShift.START_NIGHT) {
                
                if (this.getBreakTime().getEndBreak().getHour().getHour() < NightShift.END_NIGHT) {
                    breakLength = DateUtility.duration(this.getBreakTime().getStartBreak(),
                            this.getBreakTime().getEndBreak());
                } else {
                    Time tempTime = new Time(this.getBreakTime().getEndBreak().getDate(),
                            NightShift.END_NIGHT_SHIFT);
                    breakLength = DateUtility.duration(this.getBreakTime().getStartBreak(), tempTime);
                }
                
            //Starts and ends on the same day. The entire work is part of night time.
            } else if (this.getBreakTime().getStartBreak().getDate().getDay()
                    == this.getBreakTime().getEndBreak().getDate().getDay()
                    && this.getBreakTime().getStartBreak().getHour().getHour() < NightShift.END_NIGHT
                    && this.getBreakTime().getEndBreak().getHour().getHour() < NightShift.END_NIGHT) {
                
                breakLength = DateUtility.duration(this.getBreakTime().getStartBreak(),
                        this.getBreakTime().getEndBreak());
                
            //Starts and ends on the same day. Start time is part of night time, but end time not.
            } else if (this.getBreakTime().getStartBreak().getDate().getDay()
                    == this.getBreakTime().getEndBreak().getDate().getDay()
                    && this.getBreakTime().getStartBreak().getHour().getHour() < NightShift.END_NIGHT
                    && this.getBreakTime().getEndBreak().getHour().getHour() >= NightShift.END_NIGHT) {
                
                Time tempTime = new Time(this.getWorkTime().getEndWork().getDate(), NightShift.END_NIGHT_SHIFT);
                breakLength = DateUtility.duration(this.getBreakTime().getStartBreak(), tempTime);
            }
        }
        return breakLength;
    }
    
    /**
     * Checks if the break is valid. Employees working under six hours don't
     * need a brake. When working from 6 to 9 hours a break of at least 30
     * minutes is necessary. When working over 9 hours a break of at least
     * 45 minutes is necessary. Employees can't work over six consecutive
     * hours without taking a break.
     * 
     * @return True if the employee needs a break and doesn't work more
     * than six consecutive hours without taking a break.
     */
    private boolean isValidBreak() {
        if ((workTime.getNeedsShortBreak() && breakTime.isShortBreak())
                || (workTime.getNeedsLongBreak() && breakTime.isLongBreak())) {
            
            return DateUtility.duration(workTime.getStartWork(), breakTime.getStartBreak()) <= MAX_WORK_INTERVALL
                    && DateUtility.duration(breakTime.getEndBreak(), workTime.getEndWork()) <= MAX_WORK_INTERVALL;
            
        } else if (!workTime.getNeedsShortBreak() && !workTime.getNeedsLongBreak()) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates if the work start and end times and the break start and end times are allocated
     * correctly.
     * @return True if the times are in chronological order.
     */
    private boolean isValidTime() {
        if (hasBreak) {
            return workTime.getStartWork().compareTo(breakTime.getStartBreak()) <= 0
                    && breakTime.getEndBreak().compareTo(workTime.getEndWork()) <= 0
                    && breakTime.getStartBreak().compareTo(breakTime.getEndBreak()) <= 0;
        }
        return workTime.getStartWork().compareTo(workTime.getEndWork()) <= 0;
    }
    
    /**
     * Getter for hasBreak. Returns if the planned work day has a break
     * or not.
     * @return True if the planned work day has a break.
     */
    public boolean getHasBreak() {
        return this.hasBreak;
    }
    
    /**
     * Getter for validBreak. Returns if the break is valid or not.
     * @return True if the break is valid.
     */
    public boolean getValidBreak() {
        return this.validBreak;
    }
    
    /**
     * Getter for validTimes. Returns if the times are in chronological order or not.
     * @return True if the times are allocated correctly.
     */
    public boolean getValidTimes() {
        return this.validTimes;
    }
    
    /**
     * Getter for the worked time. Contains the break time.
     * @return The planned work time
     */
    public WorkTime getWorkTime() {
        return this.workTime;
    }
    
    /**
     * Getter for the break time that is inside the workTime interval.
     * @return The planned brake.
     */
    public BreakTime getBreakTime() {
        return this.breakTime;
    }
}