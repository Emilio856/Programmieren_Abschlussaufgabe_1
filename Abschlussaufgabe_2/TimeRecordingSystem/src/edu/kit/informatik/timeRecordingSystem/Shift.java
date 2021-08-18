package edu.kit.informatik.timeRecordingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Hour;
import edu.kit.informatik.timeRecordingSystem.time.Time;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;

/**
 * Models a shift as an abstract class. A shift can be a regular,
 * night, Sunday or holiday shift and records a session of work
 * from an employee.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public abstract class Shift implements Cloneable {
    
    /**
     * Error message in case a non production worker tries to work on a Sunday.
     */
    public static final String NO_SUNDAY_WORK = "only production workers can work on Sundays!";
    /**
     * Error message in case a non production worker tries to work in a holiday.
     */
    public static final String NO_HOLIDAY_WORK = "only production workers can work on holidays!";
    /**
     * Error message in case the maximal working time is being exceeded.
     */
    protected static final String MAX_DAILY_WORK = "the working time can't exceed ten hours!";
    /**
     * Error message in case not a single minute is being planned to work during a shift.
     */
    protected static final String MIN_DAILY_WORK = "an employee must work at least a minute during a shift!";
    /**
     * Error message in case the average work in six months or 24 weeks is being exceeded.
     */
    protected static final String AVERAGE_WORK = "the average working time can't exceed 8 daily hours!";
    /**
     * Error message in case the rest time is not being respected.
     */
    protected static final String REST_TIME = "the rest time is not valid!";
    /**
     * Error message in case a shift is not valid because of its characteristics.
     */
    protected static final String NOT_VALID_SHIFT = "the shift is not valid!";
    /**
     * A employee is not allowed to work more than eight hours a day in average.
     */
    protected static final int AVERAGE_DAILY_WORK = 480;
    /**
     * Used to calculate an average in one month.
     */
    protected static final int AVERAGE_ONE_MONTH = 1;
    /**
     * Used to calculate an average in six months.
     */
    protected static final int AVERAGE_SIX_MONTHS = 6;
    /**
     * Used to calculate an average in four weeks.
     */
    protected static final int AVERAGE_FOUR_WEEKS = 4;
    /**
     * Used to calculate an average in twenty four weeks.
     */
    protected static final int AVERAGE_TWENTYFOUR_WEEKS = 24;
    /**
     * The number of weeks considered for the average rest time.
     */
    private static final int REST_TIME_WEEKS = 4;
    /**
     * The number of months considered for the average rest time.
     */
    private static final int REST_TIME_MONTHS = 1;
    /**
     * The average rest time has to be at least 12 hours long.
     */
    private static final int AV_REST_TIME = 720;
    
    /**
     * The time worked. Doesn't need to be final, is already immutable.
     */
    protected WorkTime workTime;
    
    /**
     * Break time during work in a shift. Doesn't need to be final, is already immutable.
     */
    protected BreakTime breakTime;
    
    /**
     * Shows if a shift has a break or not.
     */
    protected final boolean hasBreak;
    
    /**
     * Constructor for a shift with a break.
     * @param workTime The time worked.
     * @param breakTime The time in which the employee took a break.
     */
    public Shift(WorkTime workTime, BreakTime breakTime) {
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.hasBreak = true;
    }
    
    /**
     * Constructor for a shift without a break.
     * @param workTime The time worked.
     */
    public Shift(WorkTime workTime) {
        this.workTime = workTime;
        this.hasBreak = false;
    }
    
    /**
     * Abstract method to check if a shift is valid or not. The method is overridden
     * in each of the subclasses to adjust the requirements of a valid shift.
     * @param workDay The work day that might be added.
     * @param workDays Contains the employees recorded shifts.
     * @param holidays The employees holidays.
     * @return  True if the shift meets all requirements to be a valid shift.
     */
    public abstract boolean isValidShift(WorkDay workDay, WorkDays workDays, Holidays holidays);
    
    /**
     * Abstract method to return the reason why the shift is not valid. The method is
     * overridden in each of the subclasses.
     * @param workDay The work day that might be added.
     * @param workDays Contains the employees recorded shifts.
     * @param holidays The employees holidays.
     * @return An error message explaining the error in the shift.
     */
    public abstract String invalidShift(WorkDay workDay, WorkDays workDays, Holidays holidays);
    
    /**
     * Overrides clone() to make a deep copy of an object of the type Shift.
     */
    @Override
    public Shift clone() {
        try {  
            return (Shift) super.clone();  
        } catch (CloneNotSupportedException e) { 
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Calculates the work duration of the shift in minutes and formats it to an hour:minute representation.
     * @return The duration work duration of a shift in an hour:minute representation.
     */
    public Hour formatDuration() {
        int duration;
        if (!hasBreak) {
            duration = DateUtility.duration(workTime.getStartWork(), workTime.getEndWork());
        } else {
            duration = DateUtility.duration(workTime.getStartWork(), breakTime.getStartBreak())
                    + DateUtility.duration(breakTime.getEndBreak(), workTime.getEndWork());
        }
        int hours = duration / Time.CALCULATION_RULES_60;
        int minutes = duration % Time.CALCULATION_RULES_60;
        return new Hour(hours, minutes);
    }
    
    /**
     * Checks that the average daily work doesn't within a given amount of weeks doesn't
     * surpass a given amount of minutes.
     * @param weeks The amount of weeks within the current date in which the average must be respected.
     * @param average The desired maximal average of worked minutes.
     * @param workDays Contains the employees recorded shifts.
     * @param workDay The work day that will be analyzed.
     * @return True if the daily average is respected.
     */
    public boolean dailyWorkWeeks(int weeks, int average, WorkDays workDays, WorkDay workDay) {
        ArrayList<Shift> daysWorked = workDays.copyOfWorkDays();
        Date lowerBound = DeadlinesUtility.weeksBack(weeks, workDay.getWorkTime().getStartWork().getDate());
        Date upperBound = DeadlinesUtility.weeksForward(weeks, workDay.getWorkTime().getStartWork().getDate());
        int counter = 0;
        int countWork = 0;
        for (Shift s : daysWorked) {
            if (DeadlinesUtility.beetweenDates(lowerBound, upperBound, s.getWorkTime().getStartWork().getDate())
                    && DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                countWork += DeadlinesUtility.workDuration(s);
                counter++;
            } else if (DeadlinesUtility.beetweenDates(lowerBound, upperBound, s.getWorkTime().getStartWork().getDate())
                    && !DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                Date tempDate = s.getWorkTime().getStartWork().getDate();
                Time time = new Time(tempDate, WorkDay.END_DAY);
                countWork += DateUtility.duration(s.getWorkTime().getStartWork(), time);
                counter++;
            } else if (!DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    s.getWorkTime().getStartWork().getDate())
                    && DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                Date tempDate = s.getWorkTime().getStartWork().getDate();
                Time time = new Time(tempDate, WorkDay.END_DAY);
                countWork += DateUtility.duration(time, s.getWorkTime().getEndWork());
                counter++;
            }
        }
        if (countWork > 0 && counter > 0) {
            return countWork / counter <= average;
        }
        return true;
    }
    
    /**
     * Checks that the average daily work doesn't within a given amount of months doesn't
     * surpass a given amount of minutes.
     * @param months The amount of months within the current date in which the average must be respected
     * @param average The desired maximal average of worked minutes.
     * @param workDays Contains the employees recorded shifts.
     * @param workDay The work day that will be analyzed.
     * @return true if the daily average is respected.
     */
    public boolean dailyWorkMonths(int months, int average, WorkDays workDays, WorkDay workDay) {
        ArrayList<Shift> daysWorked = workDays.copyOfWorkDays();
        Date lowerBound = DeadlinesUtility.monthsBack(months, workDay.getWorkTime().getStartWork().getDate());
        Date upperBound = DeadlinesUtility.monthsForward(months, workDay.getWorkTime().getStartWork().getDate());
        int counter = 0;
        int countWork = 0;
        for (Shift s : daysWorked) {
            if (DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    s.getWorkTime().getStartWork().getDate())
                    && DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                countWork += DeadlinesUtility.workDuration(s);
                counter++;
            } else if (DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    s.getWorkTime().getStartWork().getDate())
                    && !DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                Date tempDate = s.getWorkTime().getStartWork().getDate();
                Time time = new Time(tempDate, WorkDay.END_DAY);
                countWork += DateUtility.duration(s.getWorkTime().getStartWork(), time);
                counter++;
            } else if (!DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    s.getWorkTime().getStartWork().getDate())
                    && DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            s.getWorkTime().getEndWork().getDate())) {
                Date tempDate = s.getWorkTime().getStartWork().getDate();
                Time time = new Time(tempDate, WorkDay.END_DAY);
                countWork += DateUtility.duration(time, s.getWorkTime().getEndWork());
                counter++;
            }
        }
        if (countWork > 0 && counter > 0) {
            return countWork / counter <= average;
        }
        return true;
    }
    
    /**
     * The maximal daily work can't exceed ten hours. The method iterates over all shifts and checks if this
     * limit is respected.
     * @param workDays Contains the employees recorded shifts.
     * @return True if the work limit isn't surpassed at any day.
     */
    public boolean maxWork(WorkDays workDays) {
        ArrayList<Shift> arrCopy = workDays.copyOfWorkDays();
        Map<Date, Integer> work = new HashMap<>();
        for (Shift wd : arrCopy) {
            if (wd.getWorkTime().getStartWork().getDate().equals(wd.getWorkTime().getEndWork().getDate())) {
                if (work.containsKey(wd.getWorkTime().getStartWork().getDate())) {
                    int tempWork1 = work.get(wd.getWorkTime().getStartWork().getDate());
                    int tempWork2 = DeadlinesUtility.workDuration(wd);
                    work.put(wd.getWorkTime().getStartWork().getDate(), tempWork1 + tempWork2);
                } else {
                    work.put(wd.getWorkTime().getStartWork().getDate(), DeadlinesUtility.workDuration(wd));
                }
            } else {
                if (work.containsKey(wd.getWorkTime().getStartWork().getDate())) {
                    int tempWork1 = work.get(wd.getWorkTime().getStartWork().getDate());
                    int tempWork2 = DeadlinesUtility.workDuration(wd, true);
                    work.put(wd.getWorkTime().getStartWork().getDate(), tempWork1 + tempWork2);
                } else {
                    work.put(wd.getWorkTime().getStartWork().getDate(), DeadlinesUtility.workDuration(wd, true));
                }
                if (work.containsKey(wd.getWorkTime().getEndWork().getDate())) {
                    int tempWork1 = work.get(wd.getWorkTime().getEndWork().getDate());
                    int tempWork2 = DeadlinesUtility.workDuration(wd, false);
                    work.put(wd.getWorkTime().getEndWork().getDate(), tempWork1 + tempWork2);
                } else {
                    work.put(wd.getWorkTime().getEndWork().getDate(), DeadlinesUtility.workDuration(wd, false));
                }
            }
        }
        if (work.size() > 0) {
            for (Map.Entry<Date, Integer> entry : work.entrySet()) {
                if (entry.getValue() > WorkDay.MAX_WORK) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Analyzes the average rest time around 4 weeks. The average rest time in four weeks
     * has to be at least 12 hours.
     * @param workDays Contains the employees recorded shifts.
     * @param workDay The work day that will be analyzed.
     * @return True if the average rest time in four weeks is at least 12 hours.
     */
    public boolean restTimeWeeks(WorkDays workDays, Shift workDay) {
        Date lowerBound = DeadlinesUtility.weeksBack(REST_TIME_WEEKS,
                workDay.getWorkTime().getStartWork().getDate());
        Date upperBound = DeadlinesUtility.weeksForward(REST_TIME_WEEKS,
                workDay.getWorkTime().getStartWork().getDate());
        ArrayList<Shift> arrCopy = workDays.copyOfWorkDays();
        int countRest = 0;
        int counter = 0;
        for (int i = 0; i < arrCopy.size() - 1; i++) {
            if (DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    arrCopy.get(i).getWorkTime().getStartWork().getDate())
                    || DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            arrCopy.get(i).getWorkTime().getEndWork().getDate())) {
                
                if (DateUtility.duration(arrCopy.get(i).getWorkTime().getEndWork(),
                        arrCopy.get(i + 1).getWorkTime().getStartWork()) < WorkDay.MIN_REST_TIME) {
                    return false;
                } else {
                    countRest += DateUtility.duration(arrCopy.get(i).getWorkTime().getStartWork(),
                            arrCopy.get(i + 1).getWorkTime().getEndWork());
                    counter++;
                }
            }
        }
        if (counter > 0 && countRest > 0) {
            return countRest / counter >= AV_REST_TIME;
        }
        return true;
    }
    
    /**
     * Analyzes the average rest time around one month. The average rest time in one month
     * has to be at least 12 hours.
     * @param workDays Contains the employees recorded shifts.
     * @param workDay The day that will be analyzed.
     * @return True if the average rest time in a month is at least 12 hours.
     */
    public boolean restTimeMonths(WorkDays workDays, WorkDay workDay) {
        Date lowerBound = DeadlinesUtility.monthsBack(REST_TIME_MONTHS,
                workDay.getWorkTime().getStartWork().getDate());
        Date upperBound = DeadlinesUtility.monthsForward(REST_TIME_MONTHS,
                workDay.getWorkTime().getStartWork().getDate());
        ArrayList<Shift> arrCopy = workDays.copyOfWorkDays();
        int countRest = 0;
        int counter = 0;
        for (int i = 0; i < arrCopy.size() - 1; i++) {
            if (DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                    arrCopy.get(i).getWorkTime().getStartWork().getDate())
                    || DeadlinesUtility.beetweenDates(lowerBound, upperBound,
                            arrCopy.get(i).getWorkTime().getEndWork().getDate())) {
                
                if (DateUtility.duration(arrCopy.get(i).getWorkTime().getEndWork(),
                        arrCopy.get(i + 1).getWorkTime().getStartWork()) < WorkDay.MIN_REST_TIME) {
                    return false;
                } else {
                    countRest += DateUtility.duration(arrCopy.get(i).getWorkTime().getStartWork(),
                            arrCopy.get(i + 1).getWorkTime().getEndWork());
                    counter++;
                }
            }
        }
        if (counter > 0 && countRest > 0) {
            return countRest / counter >= AV_REST_TIME;
        }
        return true;
    }
    
    /**
     * Checks if a rest day is being compensated after having worked on a Sunday or holiday.
     * @param weeks The amount of weeks within the rest day has to be compensated.
     * @param workDays Contains the employees recorded shifts.
     * @param workDay The work day that will be analyzed.
     * @param holidays The employees holidays.
     * @return True if the rest day is being compensated.
     */
    public boolean compensateDay(int weeks, WorkDays workDays, WorkDay workDay, Holidays holidays) {
        Date lowerBound = DeadlinesUtility.weeksBack(weeks, workDay.getWorkTime().getStartWork().getDate());
        Date upperBound = DeadlinesUtility.weeksForward(weeks, workDay.getWorkTime().getStartWork().getDate());
        ArrayList<Shift> arrCopy = workDays.copyOfWorkDays();
        ArrayList<Date> datesNoWork = new ArrayList<>();
        boolean containsDay;
        Date date = lowerBound;
        while (date.compareTo(upperBound) <= 0) {
            containsDay = false;
            for (Shift wd : arrCopy) {
                if (wd.getWorkTime().getStartWork().getDate().equals(date)
                        && wd.getWorkTime().getEndWork().getDate().equals(date)) {
                    containsDay = true;
                    break;
                }
            }
            if (!containsDay && DateUtility.dayOfWeek(date) != WorkDay.SUNDAY && !holidays.isHoliday(date)) {
                datesNoWork.add(date);
            }
            date = DateUtility.nextDay(date);
        }
        if (datesNoWork.size() > 0) {
            return replacementDay(datesNoWork, workDays);
        }
        return false;
    }
    
    /**
     * Checks if the work performed during a shift is on a Sunday or not.
     * @return True if the shift is during a Sunday.
     */
    public boolean isSunday() {
        if (this.hasBreak) {
            if (this.getWorkTime().getStartWork().equals(this.getBreakTime().getStartBreak())
                    && this.getBreakTime().getEndBreak().getHour().equals(WorkDay.END_DAY)) {
                return DateUtility.dayOfWeek(this.getWorkTime().getEndWork().getDate()) == WorkDay.SUNDAY;
            } else if (this.getBreakTime().getEndBreak().equals(this.workTime.getEndWork())
                    && this.getBreakTime().getStartBreak().getHour().equals(WorkDay.START_DAY)) {
                return DateUtility.dayOfWeek(this.getWorkTime().getStartWork().getDate()) == WorkDay.SUNDAY;
            }
            return false;
        } else {
            return DateUtility.dayOfWeek(this.getWorkTime().getStartWork().getDate()) == WorkDay.SUNDAY
                    || DateUtility.dayOfWeek(this.getWorkTime().getEndWork().getDate()) == WorkDay.SUNDAY;
        }
    }
    
    /**
     * Checks if a the work performed during a shift is on a holiday or not.
     * @param holidays The days that count as holidays.
     * @return True if the shift is during a holiday.
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
     * Getter for the work time of the shift.
     * @return The shifts work time of the type WorkTime.
     */
    public WorkTime getWorkTime() {
        return this.workTime;
    }
    
    /**
     * Getter for the break time of the shift.
     * @return The shifts break time of the type BreakTime.
     */
    public BreakTime getBreakTime() {
        return this.breakTime;
    }
    
    /**
     * Getter for hasBreak.
     * @return True if the shift has a break, else false.
     */
    public boolean getHasBreak() {
        return this.hasBreak;
    }
    
    /**
     * Auxiliary method to check the employee has a rest day after having worked on a Sunday or holiday
     * @param dates The dates to check in the interval.
     * @param workDays The current employees shifts plus the planned one.
     * @return True if the employee receives a rest day.
     */
    private boolean replacementDay(ArrayList<Date> dates, WorkDays workDays) {
        if (workDays.copyOfWorkDays().size() < Main.INDEX_TWO) {
            return true;
        }
        for (Date date : dates) {
            Date dayAfter = DateUtility.nextDay(date);
            Date dayBefore = DateUtility.dayBefore(date);
            Time beforeTime = new Time(dayBefore, WorkDay.LOWER_BOUND_REST_TIME);
            Time afterTime = new Time(dayAfter, WorkDay.UPPER_BOUND_REST_TIME);
            ArrayList<Shift> copy = workDays.copyOfWorkDays();
            for (int i = 0; i < copy.size() - 1; i++) {
                if (copy.get(i).getWorkTime().getEndWork().compareTo(beforeTime) <= 0
                        && copy.get(i + 1).getWorkTime().getStartWork().compareTo(afterTime) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }
}