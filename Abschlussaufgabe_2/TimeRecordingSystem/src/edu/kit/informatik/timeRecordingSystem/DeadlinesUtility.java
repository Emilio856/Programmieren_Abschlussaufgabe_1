package edu.kit.informatik.timeRecordingSystem;

import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Time;

/**
 * Utility class for the deadlines. Contains auxiliary methods
 * that are necessary to control the working guidelines.
 * 
 * Class is set as final and has a private constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public final class DeadlinesUtility {
    
    /**
     * The amount of days a week has.
     */
    private static final int DAYS_IN_WEEK = 7;
    
    /**
     * Private empty constructor for a utility class.
     */
    private DeadlinesUtility() {
        
    }
    
    /**
     * Checks if a given date is between two other dates using
     * the overridden compareTo method.
     * 
     * @param date1 The lower bound date.
     * @param date2 The upper bound date.
     * @param date3 The date to be compared with the other two.
     * @return True if date3 is between date1 and date2
     */
    public static boolean beetweenDates(Date date1, Date date2, Date date3) {
        
        return date1.compareTo(date3) <= 0 && date2.compareTo(date3) >= 0;
    }
    
    /**
     * Calculates the time an employee plans to work in a work day. If the
     * work day is separated into two parts the method can calculate one of
     * them depending on the input.
     * @param workDay The planned work day.
     * @param firstDay Calculates the work during the first day when true.
     * Else it calculates the work during the second day.
     * @return The amount of minutes an employee plans to work in one of both days
     */
    public static int workDuration(Shift workDay, boolean firstDay) {
        int duration = 0;
        if (firstDay) {
            if (!workDay.getHasBreak()
                    || workDay.getBreakTime().getStartBreak().getDate()
                    != workDay.getWorkTime().getStartWork().getDate()) {
                duration = DateUtility.duration(workDay.getWorkTime().getStartWork(),
                        new Time(workDay.getWorkTime().getStartWork().getDate(), WorkDay.END_DAY));
                
            } else if (workDay.getBreakTime().getStartBreak().getDate()
                    == workDay.getWorkTime().getStartWork().getDate()
                    && workDay.getBreakTime().getEndBreak().getDate()
                    == workDay.getWorkTime().getStartWork().getDate()) {
                duration = DateUtility.duration(workDay.getWorkTime().getStartWork(),
                        new Time(workDay.getWorkTime().getStartWork().getDate(), WorkDay.END_DAY))
                        - DateUtility.duration(workDay.getBreakTime().getStartBreak(),
                                workDay.getBreakTime().getEndBreak());
                
            } else if (workDay.getBreakTime().getStartBreak().getDate()
                    == workDay.getWorkTime().getStartWork().getDate()
                    && workDay.getBreakTime().getEndBreak().getDate()
                    != workDay.getWorkTime().getStartWork().getDate()) {
                duration = DateUtility.duration(workDay.getWorkTime().getStartWork(),
                        workDay.getBreakTime().getStartBreak());
            }
        } else {
            if (!workDay.getHasBreak()
                    || workDay.getBreakTime().getStartBreak().getDate()
                    != workDay.getWorkTime().getEndWork().getDate()) {
                duration = DateUtility.duration(new Time(workDay.getWorkTime().getEndWork().getDate(),
                        WorkDay.START_DAY), workDay.getWorkTime().getEndWork());
                
            } else if (workDay.getBreakTime().getStartBreak().getDate()
                    == workDay.getWorkTime().getEndWork().getDate()) {
                duration = DateUtility.duration(new Time(workDay.getWorkTime().getEndWork().getDate(),
                        WorkDay.START_DAY), workDay.getWorkTime().getEndWork());
            } else if (workDay.getBreakTime().getStartBreak().getDate()
                    != workDay.getWorkTime().getEndWork().getDate()
                    && workDay.getBreakTime().getEndBreak().getDate()
                    == workDay.getWorkTime().getEndWork().getDate()) {
                duration = DateUtility.duration(workDay.getBreakTime().getEndBreak(),
                        workDay.getWorkTime().getEndWork());
            }
        }
        return duration;
    }
    
    /**
     * Calculates the duration of work during a shift considering that
     * the shift may have a break.
     * @param shift The shift whose work length will be calculated.
     * @return The duration of the work time in minutes.
     */
    public static int workDuration(Shift shift) {
        int duration;
        if (shift.getHasBreak()) {
            duration = DateUtility.duration(shift.getWorkTime().getStartWork(),
                    shift.getBreakTime().getStartBreak())
                    + DateUtility.duration(shift.getBreakTime().getEndBreak(),
                            shift.getWorkTime().getEndWork());
        } else {
            duration = DateUtility.duration(shift.getWorkTime().getStartWork(), shift.getWorkTime().getEndWork());
        }
        return duration;
    }
    
    /**
     * Calculates a date that is a specific amount of weeks back in time.
     * @param weeksNr The amount of weeks to go back in time.
     * @param date The current date.
     * @return A date that is weeksNr weeks in the past.
     */
    public static Date weeksBack(int weeksNr, Date date) {
        int newDay = date.getDay();
        int newMonth = date.getMonth();
        int newYear = date.getYear();
        int counter = weeksNr * DAYS_IN_WEEK;
        Date tempDate = date;
        
        while (counter != 0) {
            if (newDay > DateUtility.FIRST_DAY) {
                newDay--;
                counter--;
            } else {
                if (newMonth > Main.JAN_NUM) {
                    newMonth--;
                    newDay = DateUtility.FIRST_DAY;
                    tempDate = new Date(newDay, newMonth, newYear);
                    newDay = DateUtility.monthDays(tempDate);
                } else {
                    newMonth = Main.DEC_NUM;
                    newDay = DateUtility.LAST_DAY_31;
                    newYear--;
                }
                tempDate = new Date(newDay, newMonth, newYear);
                newDay = tempDate.getDay();
                newMonth = tempDate.getMonth();
                newYear = tempDate.getYear();
                counter--;
            }
        }
        Date newDate = new Date(newDay, newMonth, newYear);
        return newDate;
    }
    
    /**
     * Calculates a date that is a specific amount of weeks forward in time.
     * @param weeksNr The amount of weeks to go into the future.
     * @param date The current date.
     * @return A date that is weeksNr weeks into the future.
     */
    public static Date weeksForward(int weeksNr, Date date) {
        int newDay = date.getDay();
        int newMonth = date.getMonth();
        int newYear = date.getYear();
        int counter = weeksNr * DAYS_IN_WEEK;
        Date tempDate = date;
        
        while (counter != 0) {
            if (newDay < DateUtility.monthDays(date)) {
                newDay++;
                counter--;
            } else {
                if (newMonth < Main.DEC_NUM) {
                    newMonth++;
                    newDay = DateUtility.FIRST_DAY;
                } else {
                    newMonth = Main.JAN_NUM;
                    newDay = DateUtility.FIRST_DAY;
                    newYear++;
                }
                tempDate = new Date(newDay, newMonth, newYear);
                
                newDay = tempDate.getDay();
                newMonth = tempDate.getMonth();
                newYear = tempDate.getYear();
                counter--;
            }
        }
        Date newDate = new Date(newDay, newMonth, newYear);
        return newDate;
    }
    
    /**
     * Calculates a date that is a specific amount of months back in time.
     * @param monthsNr The amount of months to go back in time.
     * @param date The current date.
     * @return A date that is located monthsNr months in the past.
     */
    public static Date monthsBack(int monthsNr, Date date) {
        int counter = monthsNr;
        Date tempDate = date;
        
        while (counter != 0) {
            tempDate = oneMonthBack(date);
            counter--;
        }
        return tempDate;
    }
    
    /**
     * Calculates a date that is a specific amount of months into the future
     * @param monthsNr The amount of months to go into the future.
     * @param date The current date.
     * @return A date that is located monthsNr months into the future.
     */
    public static Date monthsForward(int monthsNr, Date date) {
        int counter = monthsNr;
        Date tempDate = date;
        
        while (counter != 0) {
            tempDate = oneMonthForward(date);
            counter--;
        }
        return tempDate;
    }
    
    /**
     * Receives a date and creates another date that is located one month back
     * in time of the parameter.
     * @param date The current date.
     * @return A date that is located one month back in time.
     */
    private static Date oneMonthBack(Date date) {
        if (date.getMonth() > Main.JAN_NUM) {
            int newMonth = date.getMonth() - 1;
            Date tempDate = new Date(DateUtility.FIRST_DAY, newMonth, date.getYear());
            int newDay;
            if (date.getDay() <= DateUtility.monthDays(tempDate)) {
                newDay = date.getDay();
            } else {
                newDay = DateUtility.monthDays(tempDate);
            }
            Date newDate = new Date(newDay, newMonth, date.getYear());
            return newDate;
        } else {
            int newMonth = Main.DEC_NUM;
            int newYear = date.getYear() - 1;
            Date tempDate = new Date(DateUtility.FIRST_DAY, newMonth, newYear);
            int newDay;
            if (date.getDay() <= DateUtility.monthDays(tempDate)) {
                newDay = date.getDay();
            } else {
                newDay = DateUtility.monthDays(tempDate);
            }
            Date newDate = new Date(newDay, newMonth, newYear);
            return newDate;
        }
    }
    
    /**
     * Receives a date and creates another date that is located one month into
     * the future of the parameter.
     * @param date The current date.
     * @return A date that is located one month into the future.
     */
    private static Date oneMonthForward(Date date) {
        if (date.getMonth() < Main.DEC_NUM) {
            int newMonth = date.getMonth() + 1;
            Date tempDate = new Date(DateUtility.FIRST_DAY, newMonth, date.getYear());
            int newDay;
            if (date.getDay() <= DateUtility.monthDays(tempDate)) {
                newDay = date.getDay();
            } else {
                newDay = DateUtility.monthDays(tempDate);
            }
            Date newDate = new Date(newDay, newMonth, date.getYear());
            return newDate;
        } else {
            int newMonth = Main.JAN_NUM;
            int newYear = date.getYear() + 1;
            Date tempDate = new Date(DateUtility.FIRST_DAY, newMonth, newYear);
            int newDay;
            if (date.getDay() <= DateUtility.monthDays(tempDate)) {
                newDay = date.getDay();
            } else {
                newDay = DateUtility.monthDays(tempDate);
            }
            Date newDate = new Date(newDay, newMonth, newYear);
            return newDate;
        }
    }
}