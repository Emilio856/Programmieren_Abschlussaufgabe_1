package edu.kit.informatik.timeRecordingSystem;

import java.util.ArrayList;

import edu.kit.informatik.timeRecordingSystem.time.BreakTime;
import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Time;
import edu.kit.informatik.timeRecordingSystem.time.WorkTime;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.Employee;

/**
 * Contains all commands that can be executed for the time recording
 * system. Can add regular, night, production and night production
 * employees. It's also possible to record working time and list the
 * working time from a specific employee or the employees that work at
 * a specific time. Contains a command to quit the program.
 * 
 * The methods returns errors in case the commands couldn't be
 * processed properly or the input was incorrect.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Commands {
    
    /**
     * Error in case the employee role doesn't exist.
     */
    private static final String ROLE_ERROR = "the role doesn't exist!";
    /**
     * Error in case the given name is incorrect.
     */
    private static final String NAME_ERROR = "the name is incorrect!";
    /**
     * Error in case the given day is incorrect.
     */
    private static final String DATE_ERROR = "the date is incorrect!";
    /**
     * Error in case one or more times are incorrect.
     */
    private static final String TIMES_ERROR = "one or more of the given times are incorrect!";
    /**
     * Error in case the given time is incorrect.
     */
    private static final String TIME_ERROR = "the given time is incorrect!";
    /**
     * Error in case the parameters are not correct.
     */
    private static final String PARAMETERS_ERROR = "incorrect parameters!";
    /**
     * Error in case no employee has a specific identification number.
     */
    private static final String IDENTIFICATION_ERROR = "no employee is registered with this identification number!";
    /**
     * Error in case an employee plans to work six hours straight.
     */
    private static final String NEEDS_BREAK = "an employee can't work for more than six consecutive"
            + " hours without taking a break";
    /**
     * Defines the status of the program. Ends the program when true.
     */
    private boolean quit;
    /**
     * A company that stores the employees. The commands are executed on the company.
     */
    private Company company;
    
    /**
     * Constructor for an object of the type Commands. Sets quit to false in order
     * to not end the program, initializes the company and assigns the holidays.
     * @param holidays
     */
    public Commands(Holidays holidays) {
        quit = false;
        company = new Company(holidays);
    }
    
    /**
     * Adds a new employee with a first and last name, a date of birth and a role.
     * Each employee receives a unique identification number that starts with one
     * and is then incremented with each new employee. 
     * 
     * If it was possible to create the employee a message with the employees
     * identification number is returned. In case of an error an explanatory
     * message is returned.
     * 
     * @param role The employees role (regular, night, production, night production)
     * @param firstName The employees first name.
     * @param lastName The employees second name.
     * @param dateOfBirth The date of birth of the employee
     * @return A success message with the employees identification number or an error
     * message, depending on the input.
     */
    public String addEmployee(String role, String firstName, String lastName, String dateOfBirth) {
        Name name;
        Date date;
        
        if (validRole(role)
                && isValidName(firstName)
                && isValidName(lastName)
                && DateUtility.validDate(dateOfBirth)) {
            
            name = new Name(firstName, lastName);
            date = DateUtility.separateDate(dateOfBirth);
            
            if (DateUtility.existingDate(date)) {
                int identification = company.createEmployee(role, name, date);
                return Main.REGISTER_OUT + identification + Main.POINT;
                
            } else {
                return Main.ERROR + DATE_ERROR;
            }
            
        } else if (!validRole(role)) {
            return Main.ERROR + ROLE_ERROR;
            
        } else if (!isValidName(firstName) || !isValidName(lastName)) {
            return Main.ERROR + NAME_ERROR;
            
        } else {
            return Main.ERROR + PARAMETERS_ERROR;
        }
    }
    
    /**
     * This command allows to record working time for an employee that is identified
     * by its identification number. The time between the break has to be subtracted
     * from the time between the start and end of the work to calculate the amount
     * of worked time.
     * 
     * Returns an error if the input doesn't match the specifications or the working
     * time couldn't be recorded successfully. If the working time was registered
     * successfully a message showing the works duration and employees identification
     * number is returned.
     * 
     * @param identification The employees identification number.
     * @param startWork The starting time for the work.
     * @param endWork The ending time for the work.
     * @param startBreak The breaks starting time.
     * @param endBreak The breaks ending time.
     * @return A specific error message in case it was not possible to record the
     * working time. Else a message with the work duration and employees id is
     * returned.
     */
    public String work(int identification, String startWork, String endWork, String startBreak, String endBreak) {
        if (DateUtility.validDateHour(startWork)
                && DateUtility.validDateHour(endWork)
                && DateUtility.validDateHour(startBreak)
                && DateUtility.validDateHour(endBreak)
                && company.hasEmployee(identification)) {
            Time startWorkTime = DateUtility.separateTime(startWork);
            Time endWorkTime = DateUtility.separateTime(endWork);
            Time startBreakTime = DateUtility.separateTime(startBreak);
            Time endBreakTime = DateUtility.separateTime(endBreak);
            WorkTime workTime = new WorkTime(startWorkTime, endWorkTime);
            BreakTime breakTime = new BreakTime(startBreakTime, endBreakTime);
            WorkDay workDay = new WorkDay(workTime, breakTime);
            
            if (DateUtility.validStartHour(workTime.getStartWork().getHour())
                    && DateUtility.validEndHour(workTime.getEndWork().getHour())
                    && DateUtility.validStartHour(breakTime.getStartBreak().getHour())
                    && DateUtility.validEndHour(breakTime.getEndBreak().getHour())
                    && DateUtility.existingDate(startWorkTime.getDate())
                    && DateUtility.existingDate(endWorkTime.getDate())
                    && DateUtility.existingDate(startBreakTime.getDate())
                    && DateUtility.existingDate(endBreakTime.getDate())
                    && workDay.getValidBreak()
                    && workDay.getValidTimes()) {
                
                String output = company.employeeWork(identification, workDay);
                if (recordWorkOutput(output)) {
                    return Main.RECORD_WORK_OUT_1 + output
                        + Main.RECORD_WORK_OUT_2 + identification + Main.POINT;
                }
                return output;
            }
        } else if (!company.hasEmployee(identification)) {
            return Main.ERROR + IDENTIFICATION_ERROR;
        }
        return Main.ERROR + TIMES_ERROR;
    }
    
    /**
     * Overloads the previous work method. The functionality is the same just that this
     * method allows to record work that doesn't contain a brake. As no brake is
     * registered the maximal duration is six hours.
     * 
     * Returns an error if the input doesn't match the specifications or the working
     * time couldn't be recorded successfully. If the working time was registered
     * successfully a message showing the works duration and employees identification
     * number is returned.
     * 
     * @param identification The employees identification number
     * @param startWork The starting time for the work.
     * @param endWork The ending time for the work.
     * @return A specific error message in case it was not possible to record the
     * working time. Else a message with the work duration and employees id is
     * returned.
     */
    public String work(int identification, String startWork, String endWork) {
        if (DateUtility.validDateHour(startWork) && DateUtility.validDateHour(endWork)
                && company.hasEmployee(identification)) {
            
            Time startWorkTime = DateUtility.separateTime(startWork);
            Time endWorkTime = DateUtility.separateTime(endWork);
            WorkTime workTime = new WorkTime(startWorkTime, endWorkTime);
            WorkDay workDay = new WorkDay(workTime);
            
            if (DateUtility.duration(startWorkTime, endWorkTime) > 360) {
                return Main.ERROR + NEEDS_BREAK;
            }
            
            if (DateUtility.validStartHour(workTime.getStartWork().getHour())
                    && DateUtility.validEndHour(workTime.getEndWork().getHour())
                    && DateUtility.existingDate(startWorkTime.getDate())
                    && DateUtility.existingDate(endWorkTime.getDate())
                    && workDay.getValidTimes()) {
                
                String output = company.employeeWork(identification, workDay);
                if (recordWorkOutput(output)) {
                    return Main.RECORD_WORK_OUT_1 + output
                        + Main.RECORD_WORK_OUT_2 + identification + Main.POINT;
                }
                return output;
            }
        } else if (!company.hasEmployee(identification)) {
            return Main.ERROR + IDENTIFICATION_ERROR;
        }
        return Main.ERROR + TIMES_ERROR;
    }
    
    /**
     * This command lists the recorded work of an employee who is identified by its
     * identification number. The recorded work is listed line by line. They are sorted
     * in ascending starting time of the shifts. If an employee hasn't registered work
     * the message "EMPTY" is returned.
     * 
     * @param identification The employees identification number whose work will be listed.
     * @return The recorded work line by line or "EMPTY" if the employee hasn't recorded work.
     */
    public String workingTimeEmployee(int identification) {
        if (!company.hasEmployee(identification)) {
            return Main.ERROR + IDENTIFICATION_ERROR;
        }
        
        ArrayList<Shift> shifts = company.employeeShifts(identification);
        String output = Main.EMPTY_SEPARATOR;
        
        for (int i = 0; i < shifts.size(); i++) {
            output += shifts.get(i).formatDuration().toString()
                    + Main.SPACE_SEPARATOR
                    + shifts.get(i).getWorkTime().getStartWork().toString()
                    + Main.SPACE_SEPARATOR
                    + shifts.get(i).getWorkTime().getEndWork().toString();
            if (shifts.get(i).hasBreak) {
                output += Main.SPACE_SEPARATOR
                        + shifts.get(i).getBreakTime().getStartBreak().toString()
                        + Main.SPACE_SEPARATOR
                        + shifts.get(i).getBreakTime().getEndBreak();
            }
            if (i + 1 < shifts.size()) {
                output += '\n';
            }
        }
        return output;
    }
    
    /**
     * This command lists all employees that worked during a specified time. The output
     * is returned line by line and is sorted by identification number. If no employee
     * recorded work at the given time the message "EMPTY" is returned
     * 
     * @param time The time to be analyzed.
     * @return The employees with recorded time at the Time time or "EMPTY" if no employee
     * recorded work at the time.
     */
    public String workAtTime(String time) {
        if (DateUtility.validDateHour(time)) {
            Time workTime = DateUtility.separateTime(time);
            if (DateUtility.existingDate(workTime.getDate()) && DateUtility.validEndHour(workTime.getHour())) {
                ArrayList<Employee> employees = company.employeeTime(workTime);
                String output = printEmployees(employees);
                return output;
            }
        }
        return Main.ERROR + TIME_ERROR;
    }
    
    /**
     * Quits the time recording system and finishes the program if quit is set to
     * true.
     * @param quit The new value for quit.
     */
    public void quit(boolean quit) {
        this.quit = quit;
    }
    
    /**
     * Getter for the value for quit.
     * @return The current value of quit.
     */
    public boolean getQuit() {
        return this.quit;
    }
    
    /**
     * Checks if an output corresponds the time format YYYY-MM-DDThh:mm
     * @param string The string to be compared to the time format.
     * @return True if the string has a time format.
     */
    private boolean recordWorkOutput(String string) {
        if (string.length() == Main.LENGTH_FIVE
                && DateUtility.isNumber(string.split(Main.EMPTY_SEPARATOR)[Main.INDEX_ZERO])
                && DateUtility.isNumber(string.split(Main.EMPTY_SEPARATOR)[Main.INDEX_ONE])
                && string.split(Main.EMPTY_SEPARATOR)[2].equals(Main.HOUR_SEPARATOR)
                && DateUtility.isNumber(string.split(Main.EMPTY_SEPARATOR)[Main.INDEX_THREE])
                && DateUtility.isNumber(string.split(Main.EMPTY_SEPARATOR)[Main.INDEX_FOUR])) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a given string is an abbreviation for one of the roles an employee
     * can have in a company.
     * @param role The string that will be compared to the existent roles.
     * @return True if the role exists.
     */
    private boolean validRole(String role) {
        if (EmployeeTypes.A_EMPLOYEE.getEmployeeType().equals(role)
                || EmployeeTypes.P_EMPLOYEE.getEmployeeType().equals(role)
                || EmployeeTypes.N_EMPLOYEE.getEmployeeType().equals(role)
                || EmployeeTypes.NP_EMPLOYEE.getEmployeeType().equals(role)) {
            
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a name is valid and therefore only contains letters and starts with an
     * upper case followed by lower cases.
     * @param name The name to be analyzed.
     * @return True if the name is valid.
     */
    private boolean isValidName(String name) {
        char[] charArr = name.toCharArray();
        
        if (!Character.isUpperCase(charArr[0]) || !Character.isLetter(charArr[0])) {
            return false;
        }
        for (int i = 1; i < charArr.length; i++) {
            if (!Character.isLowerCase(charArr[i]) || !Character.isLetter(charArr[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Auxiliary method to print the employees that worked at a specified
     * time. 
     * @param employees An ArrayList containing the employees.
     * @return The toString representation of all employees in the ArrayList
     * line by line.
     */
    private String printEmployees(ArrayList<Employee> employees) {
        String output = Main.EMPTY_SEPARATOR;
        for (int i = 0; i < employees.size(); i++) {
            output += employees.get(i).toString();
            if (i + 1 < employees.size()) {
                output += '\n';
            }
        }
        return output;
    }
}