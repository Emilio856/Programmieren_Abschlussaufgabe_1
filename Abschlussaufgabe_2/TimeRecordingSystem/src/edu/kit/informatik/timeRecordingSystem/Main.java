package edu.kit.informatik.timeRecordingSystem;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Main class for a time recording system for a company. The recording system is able to register
 * new employees and gives them an unique identification number. It is also possible to record work
 * for all employees and the system controls if each planned work day is valid or not depending on
 * the regulations an the employees types.
 * 
 * It is also possible to list each employees recorded work line by line or list all employees who
 * worked during a specific time. The program contains a quit command to exit.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    
    /**
     * Used for commands with five arguments.
     */
    public static final int LENGTH_FIVE = 5;
    /**
     * Index at point zero.
     */
    public static final int INDEX_ZERO = 0;
    /**
     * Index at point one.
     */
    public static final int INDEX_ONE = 1;
    /**
     * Index at point two.
     */
    public static final int INDEX_TWO = 2;
    /**
     * Index at point three.
     */
    public static final int INDEX_THREE = 3;
    /**
     * Index at point four.
     */
    public static final int INDEX_FOUR = 4;
    /**
     * Index at point five.
     */
    public static final int INDEX_FIVE = 5;
    /**
     * Index at point seven.
     */
    public static final int INDEX_SEVEN = 7;
    /**
     * Index at point ten.
     */
    public static final int INDEX_TEN = 10;
    /**
     * Index at point eleven.
     */
    public static final int INDEX_ELEVEN = 11;
    /**
     * Index at point twelve.
     */
    public static final int INDEX_TWELVE = 12;
    /**
     * Index at point thirteen.
     */
    public static final int INDEX_THIRTEEN = 13;
    /**
     * Index at point fourteen.
     */
    public static final int INDEX_FOURTEEN = 14;
    /**
     * Index at point fifteen
     */
    public static final int INDEX_FIFTHEEN = 15;
    /**
     * A separator for dates YYYY-MM-DD.
     */
    public static final String DATE_SEPARATOR = "-";
    /**
     * The separator for a date and hour.
     */
    public static final String DATE_HOUR_SEPARATOR = "T";
    /**
     * The separator for an hours hour and minute.
     */
    public static final String HOUR_SEPARATOR = ":";
    /**
     * A simple space separator used to separate inputs or outputs.
     */
    public static final String SPACE_SEPARATOR = " ";
    /**
     * An empty separator to split Strings.
     */
    public static final String EMPTY_SEPARATOR = "";
    /**
     * A message when an employee was registered successfully.
     */
    public static final String REGISTER_OUT = "Added new employee with identifier ";
    /**
     * A message in case an employee records work successfully.
     */
    public static final String RECORD_WORK_OUT_1 = "Recorded working time ";
    /**
     * The second part of the message in case an employee records work successfully.
     */
    public static final String RECORD_WORK_OUT_2 = " for employee ";
    /**
     * A message in case an employee hasn't recorded work yet or no employees were working
     * during a specific time.
     */
    public static final String EMPTY = "EMPTY";
    /**
     * Finishes a sentence.
     */
    public static final String POINT = ".";
    /**
     * The start of an error message in case something went wrong.
     */
    public static final String ERROR = "Error, ";
    /**
     * A dates String length.
     */
    public static final int DATE_LENGTH = 10;
    /**
     * A dates and hour String length.
     */
    public static final int DATE_HOUR_LENGTH = 16;
    /**
     * January's month number.
     */
    public static final int JAN_NUM = 1;
    /**
     * February's month number.
     */
    public static final int FEB_NUM = 2;
    /**
     * March's month number.
     */
    public static final int MAR_NUM = 3;
    /**
     * April's month number.
     */
    public static final int APR_NUM = 4;
    /**
     * May's month number.
     */
    public static final int MAY_NUM = 5;
    /**
     * June's month number.
     */
    public static final int JUN_NUM = 6;
    /**
     * July's month number.
     */
    public static final int JUL_NUM = 7;
    /**
     * August's month number.
     */
    public static final int AUG_NUM = 8;
    /**
     * September's month number.
     */
    public static final int SEP_NUM = 9;
    /**
     * October's month number.
     */
    public static final int OCT_NUM = 10;
    /**
     * November's month number.
     */
    public static final int NOV_NUM = 11;
    /**
     * December's month number.
     */
    public static final int DEC_NUM = 12;
    /**
     * Used input to register a new employee.
     */
    private static final String REGISTER = "employee";
    /**
     * Used input to record work with an employee.
     */
    private static final String RECORD_WORK = "workingtime";
    /**
     * Used input to list an employees recorded work or the employees that worked
     * during a specific time.
     */
    private static final String LIST = "list";
    /**
     * Used input to finish and exit the program
     */
    private static final String QUIT = "quit";
    /**
     * Error message in case an incorrect command is written.
     */
    private static final String NON_EXISTENT_COMMAND = "the command doesn't exist!";
    /**
     * Error message in case the holidays from the file are not valid.
     */
    private static final String HOLIDAYS_NOT_VALID = "the given file with the holidays is incorrect!";
    /**
     * Error message in case the command line arguments doesn't lead to a text file.
     */
    private static final String ARGS_NOT_VALID = "the content from the command line arguments is not valid!";
    /**
     * Used for commands with one argument.
     */
    private static final int LENGTH_ONE = 1;
    /**
     * Used for commands with two arguments.
     */
    private static final int LENGTH_TWO = 2;
    /**
     * Used for commands with four arguments.
     */
    private static final int LENGTH_FOUR = 4;
    /**
     * Used for commands with six arguments.
     */
    private static final int LENGTH_SIX = 6;
    
    /**
     * Main method for a time recording system. Receives a path to a file as only
     * argument. If args doesn't match the specifications the it is no longer used
     * for the rest of the program.
     * 
     * As long as the program isn't finished it allows the user to insert various
     * commands. Depending on the command and the regulations a success or an error
     * message is displayed.
     * 
     * @param args Can contain a path to a text file containing data about the holidays.
     */
    public static void main(String[] args) {
        Holidays holidays = null;
        
        if (args.length == LENGTH_ONE) {
            String[] myFile = Terminal.readFile(args[0]);
            holidays = new Holidays(myFile);
            if (!holidays.getValidHolidays()) {
                Terminal.printLine(ERROR +  HOLIDAYS_NOT_VALID);
            }
        } else {
            Terminal.printLine(ERROR + ARGS_NOT_VALID);
        }
        
        Commands commands = new Commands(holidays);
        while (!commands.getQuit()) {
            String in = Terminal.readLine();
            matchedCommand(in, commands);
        }
    }
    
    /**
     * Executes a given a command matching the parameters. If the command was executed successfully
     * a message with the changes is displayed. If an error occurs or the parameters don't match an
     * existent command an error message is printed.
     * 
     * @param command The information about the command to be executed.
     * @param commands Contains the methods to execute the given command.
     */
    private static void executeCommand(String command, Commands commands) {
        String[] input = command.split(Main.SPACE_SEPARATOR);
        
        if (input.length == LENGTH_FIVE
                && input[INDEX_ZERO].equals(REGISTER)) {
            Terminal.printLine(commands.addEmployee(input[INDEX_ONE], input[INDEX_TWO], input[INDEX_THREE],
                    input[INDEX_FOUR]));
            
        } else if (input.length == LENGTH_SIX
                && input[INDEX_ZERO].equals(RECORD_WORK)
                && DateUtility.isNumber(input[INDEX_ONE])) {
            Terminal.printLine(commands.work(Integer.parseInt(input[INDEX_ONE]), input[INDEX_TWO],
                    input[INDEX_THREE], input[INDEX_FOUR], input[INDEX_FIVE]));
            
        } else if (input.length == LENGTH_FOUR
                && input[0].equals(RECORD_WORK)
                && DateUtility.isNumber(input[INDEX_ONE])) {
            Terminal.printLine(commands.work(Integer.parseInt(input[INDEX_ONE]), input[INDEX_TWO],
                    input[INDEX_THREE]));
            
        } else if (input.length == LENGTH_TWO
                && input[0].equals(LIST)
                && DateUtility.isNumber(input[INDEX_ONE])) {
            if (commands.workingTimeEmployee(Integer.parseInt(input[INDEX_ONE])) == Main.EMPTY_SEPARATOR) {
                Terminal.printLine(EMPTY);
            } else {
                Terminal.printLine(commands.workingTimeEmployee(Integer.parseInt(input[1])));
            }
            
        } else if (input.length == LENGTH_TWO
                && input[INDEX_ZERO].equals(LIST)) {
            if (commands.workAtTime(input[INDEX_ONE]) == Main.EMPTY_SEPARATOR) {
                Terminal.printLine(EMPTY);
            } else {
                Terminal.printLine(commands.workAtTime(input[INDEX_ONE]));
            }
            
        } else if (input.length == LENGTH_ONE
                && input[INDEX_ZERO].equals(QUIT)) {
            commands.quit(true);
        } else {
            Terminal.printLine(ERROR + NON_EXISTENT_COMMAND);
        }
    }
    
    /**
     * Uses regular expressions to check the syntax of commands and
     * returns true if the string has the structure of a valid command.
     * @param str The string to be analyzed.
     * @return True if str has a valid structure.
     */
    private static boolean hasMatched(String str) {
        String regex1 = "\\S*\\s\\S*\\s\\S*\\s\\S*\\s\\S*";
        String regex2 = "\\S*\\s\\S*\\s\\S*\\s\\S*\\s\\S*\\s\\S*";
        String regex3 = "\\S*\\s\\S*\\s\\S*\\s\\S*";
        String regex4 = "^(0|[1-9][0-9]*)$";
        String regex5 = "\\S*\\s\\S*";
        String regex6 = "\\S*";
        String[] splittedArr = str.split(SPACE_SEPARATOR);
        
        if (splittedArr.length == LENGTH_FIVE && Pattern.matches(regex1, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_SIX && Pattern.matches(regex2, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_FOUR && Pattern.matches(regex3, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_TWO && Pattern.matches(regex5, str)
                && Pattern.matches(regex4, str.substring(INDEX_FIVE))) {
            return true;
            
        } else if (splittedArr.length == LENGTH_TWO && splittedArr[INDEX_ONE].length() == DATE_HOUR_LENGTH
                && Pattern.matches(regex5, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_ONE && Pattern.matches(regex6, str)) {
            return true;
            
        }
        Terminal.printLine(ERROR + NON_EXISTENT_COMMAND);
        return false;
    }
    
    /**
     * Searches for the command in case the syntax has a correct structure.
     * @param in The string to be analyzed.
     * @param commands Contains all the commands that can be executed.
     */
    private static void matchedCommand(String in, Commands commands) {
        if (hasMatched(in)) {
            executeCommand(in, commands);
        }
    }
}