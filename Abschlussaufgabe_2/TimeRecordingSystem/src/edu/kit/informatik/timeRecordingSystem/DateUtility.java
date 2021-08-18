package edu.kit.informatik.timeRecordingSystem;

import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Hour;
import edu.kit.informatik.timeRecordingSystem.time.Time;

/**
 * Utility class for dates. Contains several methods that help analyzing
 * the current date or return auxiliary objects of the type Date or Hour.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public final class DateUtility {
    
    /**
     * A months first day.
     */
    public static final int FIRST_DAY = 1;
    /**
     * The last day of a month with 31 days.
     */
    public static final int LAST_DAY_31 = 31;
    /**
     * The last day of a month with 28 days.
     */
    private static final int LAST_DAY_28 = 28;
    /**
     * The last day of a month with 29 days.
     */
    private static final int LAST_DAY_29 = 29;
    /**
     * The last day of a month with 30 days.
     */
    private static final int LAST_DAY_30 = 30;
    /**
     * The first hour and the first minute of a time.
     */
    private static final int HOUR_MINUTE_ZERO = 0;
    /**
     * The last hour of the day. 24:00 only valid as an end time.
     */
    private static final int LAST_HOUR = 24;
    /**
     * The last minute of an hour.
     */
    private static final int LAST_MIN = 59;
    /**
     * Used to calculate the day of the week of a certain date.
     */
    private static final int MODULO_SEVEN = 7;
    
    /**
     * Private constructor
     */
    private DateUtility() {
        
    }
    
    /**
     * Calculates if a given year is a leap year or not.
     * @param year The year that will be analyzed.
     * @return True if the given year is a leap year.
     */
    public static boolean isLeapYear(int year) {
        if ((year % Date.LEAP_YEAR_MODULO_400 == 0)
                || (year % Date.LEAP_YEAR_MODULO_4 == 0 && year % Date.LEAP_YEAR_MODULO_100 != 0)) {
            
            return true;
        }
        return false;
    }
    
    /**
     * Transforms a String that already matched the time pattern and creates an object of the type Date.
     * @param date A String containing the information about a date.
     * @return An object of the type Date containing the dates information.
     */
    public static Date separateDate(String date) {
        String[] dateArr = date.split(Main.DATE_SEPARATOR);
        Date newDate = new Date(Integer.parseInt(dateArr[Main.INDEX_TWO]), Integer.parseInt(dateArr[Main.INDEX_ONE]),
                Integer.parseInt(dateArr[Main.INDEX_ZERO]));
        return newDate;
    }
    
    /**
     * Transforms a String that already matched the time pattern and creates an object of the type Time.
     * @param workTime A String containing information about a time.
     * @return An object of the type Time containing the times information.
     */
    public static Time separateTime(String workTime) {
        Date date = separateDate(workTime.split(Main.DATE_HOUR_SEPARATOR)[Main.INDEX_ZERO]);
        Hour hour = separateHour(workTime.split(Main.DATE_HOUR_SEPARATOR)[Main.INDEX_ONE]);
        Time time = new Time(date, hour);
        
        return time;
    }
    
    /**
     * Checks if a String respects the format for a date representation of the type YYYY-MM-DD.
     * @param date The String that will be analyzed.
     * @return True if the String matches the pattern for a date.
     */
    public static boolean validDate(String date) {
        if (date.length() != Main.DATE_LENGTH
                || !date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_FOUR].equals(Main.DATE_SEPARATOR)
                || !date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_SEVEN].equals(Main.DATE_SEPARATOR)
                || !isNumber(date.split(Main.DATE_SEPARATOR)[Main.INDEX_ZERO])
                || !isNumber(date.split(Main.DATE_SEPARATOR)[Main.INDEX_ONE])
                || !isNumber(date.split(Main.DATE_SEPARATOR)[Main.INDEX_TWO])) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the a String respects the format for a time representation
     * YYYY-MM-DDTHH:MM.
     * 
     * @param date The String that will be analyzed.
     * @return True if the String matches the pattern for a time.
     */
    public static boolean validDateHour(String date) {
        if (date.length() != Main.DATE_HOUR_LENGTH
                || !date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_TEN].equals(Main.DATE_HOUR_SEPARATOR)
                || !validDate(date.split(Main.DATE_HOUR_SEPARATOR)[Main.INDEX_ZERO])
                || !date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_THIRTEEN].equals(Main.HOUR_SEPARATOR)
                || !isNumber(date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_ELEVEN])
                || !isNumber(date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_TWELVE])
                || !isNumber(date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_FOURTEEN])
                || !isNumber(date.split(Main.EMPTY_SEPARATOR)[Main.INDEX_FIFTHEEN])) {
            
            return false;
        }
        return true;
    }
    
    /**
     * Calculates if a given date actually exists or can exist in the program. This program
     * only supports years 1000 to 9999
     * @param date The date to be analyzed.
     * @return True if the date is correct and existent in the program.
     */
    public static boolean existingDate(Date date) {
        if (existingYear(date) && existingMonth(date) && existingDay(date)) {
            return true;
        }
        return false;
    }
    
    /**
     * Determines if an hour is valid for starting a time.
     * @param hour The hour to be analyzed.
     * @return True if the hour is valid as a starting point.
     */
    public static boolean validStartHour(Hour hour) {
        if (hour.getHour() >= HOUR_MINUTE_ZERO && hour.getHour() < LAST_HOUR
                && hour.getMinute() >= HOUR_MINUTE_ZERO && hour.getMinute() <= LAST_MIN) {
            return true;
        }
        return false;
    }
    
    /**
     * Determines if an hour is valid for ending a time. 24:00 is only allowed as an end time.
     * @param hour The hour to be analyzed.
     * @return True if the hour is valid.
     */
    public static boolean validEndHour(Hour hour) {
        if (hour.getHour() == LAST_HOUR && hour.getMinute() == HOUR_MINUTE_ZERO) {
            return true;
        } else if (validStartHour(hour)) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates the time between to times in minutes.
     * @param time1 The time that comes first.
     * @param time2 The time that comes second.
     * @return The amount of minutes between time1 and time2.
     */
    public static int duration(Time time1, Time time2) {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        
        if (time1.getDate().getMonth() == time2.getDate().getMonth()) {
            days = time2.getDate().getDay() - time1.getDate().getDay() - 1;
            if (days < 0) {
                days = 0;
            }
        } else if ((time1.getDate().getYear() == time2.getDate().getYear()
                && time2.getDate().getMonth() - time1.getDate().getMonth() == 1)
                || (time2.getDate().getYear() - time1.getDate().getYear() == 1
                        && time1.getDate().getMonth() == Main.DEC_NUM
                        && time2.getDate().getMonth() == Main.JAN_NUM)) {
            if (monthDays(time1.getDate()) - time1.getDate().getDay() > 0) {
                days += monthDays(time1.getDate()) - time1.getDate().getDay();
            }
            if (time2.getDate().getDay() - 1 > 0) {
                days += time2.getDate().getDay() - 1;
            }
        } else {
            //In all other cases the difference is over a month and it makes no sense to calculate
            //the exact total time. Setting the days to 30 will work just as well.
            days = 30;
        }
        
        if (time2.getDate().equals(time1.getDate())) {
            hours = time2.getHour().getHour() - time1.getHour().getHour();
            if (time2.getHour().getHour() == time1.getHour().getHour()) {
                minutes = time2.getHour().getMinute() - time1.getHour().getMinute();
            } else {
                minutes = time2.getHour().getMinute() - time1.getHour().getMinute();
            }
        } else {
            hours = Time.CALCULATION_RULES_24 - Time.UTILITY_HOURS - time1.getHour().getHour()
                    + time2.getHour().getHour();
            minutes = Time.CALCULATION_RULES_60 - time1.getHour().getMinute() + time2.getHour().getMinute();
        }
        
        if (time1.compareTo(time2) == 0) {
            days = 0;
            hours = 0;
            minutes = 0;
        }
        int totalMinutes = days * Time.CALCULATION_RULES_24 * Time.CALCULATION_RULES_60
                + hours * Time.CALCULATION_RULES_60 + minutes;
        return totalMinutes;
    }
    
    /**
     * Formats the work duration of a work day in minutes into an hour : minute representation.
     * @param workDay The work day whose length will be calculated.
     * @return A object of the type hour with the length of the work time from the work day.
     */
    public static Hour formatDuration(WorkDay workDay) {
        int duration;
        if (!workDay.getHasBreak()) {
            duration = duration(workDay.getWorkTime().getStartWork(), workDay.getWorkTime().getEndWork());
            
        } else {
            duration = duration(workDay.getWorkTime().getStartWork(), workDay.getBreakTime().getStartBreak())
                    + duration(workDay.getBreakTime().getEndBreak(), workDay.getWorkTime().getEndWork());
        }
        int hours = duration / Time.CALCULATION_RULES_60;
        int minutes = duration % Time.CALCULATION_RULES_60;
        Hour hour = new Hour(hours, minutes);
        return hour;
    }
    
    /**
     * Calculates the amount of days a month has. Most months have 30 or 31 days.
     * February can have 28 or 29 if it's a leap year.
     * @param date The date whose number of days in the month will be calculated.
     * @return The amount of days the current date has.
     */
    public static int monthDays(Date date) {
        int month = date.getMonth();
        int days = 0;
        
        if (month != Main.FEB_NUM) {
            switch (month) {
                case Main.JAN_NUM:
                case Main.MAR_NUM:
                case Main.MAY_NUM:
                case Main.JUL_NUM:
                case Main.AUG_NUM:
                case Main.OCT_NUM:
                case Main.DEC_NUM:
                    days = LAST_DAY_31;
                    break;
                case Main.APR_NUM:
                case Main.JUN_NUM:
                case Main.SEP_NUM:
                case Main.NOV_NUM:
                    days = LAST_DAY_30;
                    break;
                default:
                    break;
            }
        } else if (month == Main.FEB_NUM && date.getIsLeapYear()) {
            days = LAST_DAY_29;
        } else if (month == Main.FEB_NUM && !date.getIsLeapYear()) {
            days = LAST_DAY_28;
        }
        return days;
    }
    
    
    
    /**
     * Uses a date as a parameter to calculate its day of the week. Result is 0 for
     * Sunday, 1 for Monday etc.
     * 
     * Due to issues with the suggested formulas from the sheet it was necessary to
     * implement a different algorithm for the calculation. Instead Sakamoto's 
     * method is used for the calculation.
     * 
     * @param date The date whose day of week will be calculated.
     * @return 0 for Sunday, 1 for Monday ... 6 for Saturday.
     */
    public static int dayOfWeek(Date date) {
        int d = date.getDay();
        int m = date.getMonth();
        int y = date.getYear();
        
        //Part of Sakamoto's method
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        
        y -= (m < Main.MAR_NUM) ? 1 : 0;
        
        return ( y + y / Date.LEAP_YEAR_MODULO_4 - y / Date.LEAP_YEAR_MODULO_100
                + y / Date.LEAP_YEAR_MODULO_400 + t[m - 1] + d) % MODULO_SEVEN;
    }
    
    /**
     * Creates a new date that contains the day after the date from the parameters.
     * @param date The date whose successor will be created.
     * @return The date of the day after the given date.
     */
    public static Date nextDay(Date date) {
        int newDay = date.getDay() + 1;
        int newMonth = date.getMonth();
        int newYear = date.getYear();
        Date newDate = new Date(newDay, newMonth, newYear);
        if (existingDay(newDate)) {
            return newDate;
        } else {
            newDay = 1;
            newMonth++;
            newDate = new Date(newDay, newMonth, newYear);
            
            if (existingDay(newDate)) {
                return newDate;
            } else {
                newDay = 1;
                newMonth = 1;
                newYear++;
                newDate = new Date(newDay, newMonth, newYear);
                return newDate;
            }
        }
    }
    
    /**
     * Creates a new date that contains the day before the date from the parameters.
     * @param date The date whose predecessor will be created.
     * @return The date of the day before the given date.
     */
    public static Date dayBefore(Date date) {
        int newDay = date.getDay() - 1;
        int newMonth = date.getMonth();
        int newYear = date.getYear();
        Date newDate = new Date(newDay, newMonth, newYear);
        if (existingDay(newDate)) {
            return newDate;
        } else {
            newMonth--;
            newDay = monthDays(new Date(FIRST_DAY, newMonth, newYear));
            newDate = new Date(newDay, newMonth, newYear);
            if (existingDay(newDate)) {
                return newDate;
            } else {
                newYear--;
                newMonth = Main.DEC_NUM;
                newDay = monthDays(new Date(FIRST_DAY, newMonth, newYear));
                newDate = new Date(newDay, newMonth, newYear);
                return newDate;
            }
        }
    }
    
    /**
     * Checks if a given string can be casted into an integer and its value
     * therefore is an integer. Catches a NumberFormatException.
     * @param string The string to be analyzed.
     * @return True if the string could be parsed into an integer.
     */
    public static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Auxiliary method to check if a day exists or not. Months can have 28, 29, 30 or
     * 31 days depending on the month and year.
     * @param date The date that will be analyzed.
     * @return True if the day exists.
     */
    private static boolean existingDay(Date date) {
        if (date.getMonth() == Main.JAN_NUM || date.getMonth() == Main.MAR_NUM || date.getMonth() == Main.MAY_NUM
                || date.getMonth() == Main.JUL_NUM || date.getMonth() == Main.AUG_NUM
                || date.getMonth() == Main.OCT_NUM || date.getMonth() == Main.DEC_NUM) {
            
            if (date.getDay() >= FIRST_DAY && date.getDay() <= LAST_DAY_31) {
                return true;
            }
            return false;
            
        } else if (date.getMonth() == Main.APR_NUM || date.getMonth() == Main.JUN_NUM
                || date.getMonth() == Main.SEP_NUM || date.getMonth() == Main.NOV_NUM) {
            
            if (date.getDay() >= FIRST_DAY && date.getDay() <= LAST_DAY_30) {
                return true;
            }
            return false;
            
        } else if (date.getMonth() == Main.FEB_NUM && date.getIsLeapYear()) {
            if (date.getDay() >= FIRST_DAY && date.getDay() <= LAST_DAY_29) {
                return true;
            }
            return false;
            
        } else if (date.getMonth() == Main.FEB_NUM && !date.getIsLeapYear()) {
            if (date.getDay() >= FIRST_DAY && date.getDay() <= LAST_DAY_28) {
                return true;
            }
            return false;
            
        } else {
            return false;
        }
    }
    
    /**
     * Auxiliary method to check if a month exists or not. Months go from to December.
     * @param date The date to be analyzed.
     * @return True if the month of the date exists.
     */
    private static boolean existingMonth(Date date) {
        if (date.getMonth() >= Main.JAN_NUM && date.getMonth() <= Main.DEC_NUM) {
            return true;
        }
        return false;
    }
    
    /**
     * Auxiliary method to check if a year exists or not in the program. The time recording
     * system uses dates that go from year 1000 to year 9999
     * @param date The date to be analyzed.
     * @return True if the year exists in the program.
     */
    private static boolean existingYear(Date date) {
        if (date.getYear() >= Date.YEAR_LOWER_BOUND && date.getYear() <= Date.YEAR_UPPER_BOUND) {
            return true;
        }
        return false;
    }
    
    /**
     * Auxiliary method to separate an hour from its String format and create an object Hour.
     * @param hour The String to be separated.
     * @return The Hour object with the data from the parameter.
     */
    private static Hour separateHour(String hour) {
        String[] hourArr = hour.split(Main.HOUR_SEPARATOR);
        Hour newHour = new Hour(Integer.parseInt(hourArr[Main.INDEX_ZERO]), Integer.parseInt(hourArr[1]));
        return newHour;
    }
}