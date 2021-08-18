package edu.kit.informatik.timeRecordingSystem;

import java.util.ArrayList;
import java.util.Map;

import edu.kit.informatik.timeRecordingSystem.time.Date;
import edu.kit.informatik.timeRecordingSystem.time.Time;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.AEmployee;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.Employee;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.NEmployee;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.NPEmployee;
import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.PEmployee;

/**
 * Models a company that can register new employees but not delete them.
 * The company can also record the employees work and list it for each
 * identification number of employee. A company has a holidays calendar
 * that is initialized in the constructor.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Company {
    
    /**
     * The object of the type Employees contains all of the companies employees
     * and their data.
     */
    private Employees employees;
    
    /**
     * The companies holidays. Only production and night production employees
     * are allowed to work on these days.
     */
    private Holidays holidays;
    
    /**
     * Constructor for a company. Receives the holidays as a parameter and
     * initializes them and the companies employees.
     * @param holidays The companies holidays.
     */
    public Company(Holidays holidays) {
        this.holidays = holidays;
        employees = new Employees();
    }
    
    /**
     * Allows the company to add a new employee. Depending on the given data
     * an employee of a specific type (regular, night, production, night
     * production) is instantiated and added to the companies employees.
     * @param role The employees role.
     * @param name The employees first and last name.
     * @param date The employees date of birth.
     * @return The identification number of the new employee.
     */
    public int createEmployee(String role, Name name, Date date) {
        int employee = 0;
        if (role.equals(EmployeeTypes.A_EMPLOYEE.getEmployeeType())) {
            employee = employees.add(new AEmployee(name, date, holidays,
                    EmployeeTypes.A_EMPLOYEE.getEmployeeType()));
            
        } else if (role.equals(EmployeeTypes.P_EMPLOYEE.getEmployeeType())) {
            employee = employees.add(new PEmployee(name, date, holidays,
                    EmployeeTypes.P_EMPLOYEE.getEmployeeType()));
            
        } else if (role.equals(EmployeeTypes.N_EMPLOYEE.getEmployeeType())) {
            employee = employees.add(new NEmployee(name, date, holidays,
                    EmployeeTypes.N_EMPLOYEE.getEmployeeType()));
            
        } else if (role.equals(EmployeeTypes.NP_EMPLOYEE.getEmployeeType())) {
            employee = employees.add(new NPEmployee(name, date, holidays,
                    EmployeeTypes.NP_EMPLOYEE.getEmployeeType()));
        }
        return employee;
    }
    
    /**
     * Records work with the planned work day workDayfor an employee
     * with the given identification number. If the employee is allowed
     * to record the planned work and the shift is valid the method records
     * the players work and returns the work duration.
     * 
     * @param identification The identification number of the employee
     * that will record work.
     * @param workDay The planned work day.
     * @return The work duration if the work was recorded, else an error
     * message explaining the problem.
     */
    public String employeeWork(int identification, WorkDay workDay) {
        
        if (employees.canWork(workDay, identification)) {
            return employees.work(workDay, identification);
        }
        return employees.reasonCantWork(workDay, identification);
    }
    
    /**
     * Creates an ArrayList with all shifts from an employee with the
     * given identification number. If the employee hasn't recorded
     * work yet an empty ArrayList will be returned.
     * 
     * @param identification The employees identification number.
     * @return An ArrayList with the recorded shifts by the employee
     */
    public ArrayList<Shift> employeeShifts(int identification) {
        ArrayList<Shift> shifts = new ArrayList<>();
        shifts = employees.copyOfWorkDays(identification);
        return shifts;
    }
    
    /**
     * Creates an ArrayList with all employees that recorded work
     * containing a given time. If none of the employees has recorded
     * work at the time an empty ArrayList will be returned.
     * 
     * @param time The time during which employees might have worked.
     * @return An ArrayList with the employees that worked during the time.
     */
    public ArrayList<Employee> employeeTime(Time time) {
        ArrayList<Employee> employeesTime = new ArrayList<>();
        Map<Integer, Employee> employeesClone = employees.getEmployees();
        
        if (time.getHour().equals(WorkDay.END_DAY) || time.getHour().equals(WorkDay.START_DAY)) {
            return employeeTimeMidnight(time);
        }
        
        for (Map.Entry<Integer, Employee> entry : employeesClone.entrySet()) {
            for (Shift s : entry.getValue().getWorkDays().copyOfWorkDays()) {
                if (s.getWorkTime().getStartWork().compareTo(time) <= 0
                        && s.getWorkTime().getEndWork().compareTo(time) >= 0) {
                    employeesTime.add(entry.getValue());
                    break;
                }
            }
        }
        return employeesTime;
    }
    
    /**
     * Creates an ArrayList with all employees that recorded work containing a
     * given time that is located at the end or beginning of a day. If none of
     * the employees has recorded work at the time an empty ArrayList will be
     * returned.
     * 
     * @param time The time during which employees might have worked.
     * @return An ArrayList with the employees that worked during the time.
     */
    private ArrayList<Employee> employeeTimeMidnight(Time time) {
        ArrayList<Employee> employeesTime = new ArrayList<>();
        Map<Integer, Employee> employeesClone = employees.getEmployees();
        Time limitDay;
        
        if (time.getHour().equals(WorkDay.START_DAY)) {
            limitDay = new Time(DateUtility.dayBefore(time.getDate()), WorkDay.END_DAY);
        } else {
            limitDay = new Time(DateUtility.nextDay(time.getDate()), WorkDay.START_DAY);
        }
        
        for (Map.Entry<Integer, Employee> entry : employeesClone.entrySet()) {
            for (Shift s : entry.getValue().getWorkDays().copyOfWorkDays()) {
                
                if ((s.getWorkTime().getStartWork().compareTo(time) <= 0
                        || s.getWorkTime().getStartWork().compareTo(limitDay) <= 0)
                        && (s.getWorkTime().getEndWork().compareTo(time) >= 0
                        || s.getWorkTime().getEndWork().compareTo(limitDay) >= 0)) {
                    employeesTime.add(entry.getValue());
                    break;
                }
            }
        }
        return employeesTime;
    }
    
    /**
     * Checks if the company has an employee with the given identification number.
     * @param identification The searched identification number.
     * @return True if an employee with the given identification number works at the company.
     */
    public boolean hasEmployee(int identification) {
        return employees.containsEmployee(identification);
    }
}