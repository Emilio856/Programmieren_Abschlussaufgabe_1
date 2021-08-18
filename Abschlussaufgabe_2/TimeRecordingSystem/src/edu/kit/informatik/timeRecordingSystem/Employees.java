package edu.kit.informatik.timeRecordingSystem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.kit.informatik.timeRecordingSystem.typesOfEmployees.Employee;

/**
 * Models an object that is able to store a companies employees. The
 * employees are stored in a Map. This class also contains various
 * methods to interact with the companies employees.
 * 
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Employees implements Cloneable {
    
    /**
     * Stores the companies employees and uses their identification
     * number as key.
     */
    private Map<Integer, Employee> employees;
    
    /**
     * Constructor for Employees. Initializes the Map for the employees
     * as a new HashMap.
     */
    public Employees() {
        employees = new HashMap<>();
    }
    
    /**
     * Adds a new employee to the employees map. 
     * @param employee The new employee to be added.
     * @return The identification number of the added employee.
     */
    public int add(Employee employee) {
        employees.put(employee.getIdentification(), employee);
        return employee.getIdentification();
    }
    
    /**
     * Executes the work command on an employee with a given identification
     * number. This way encapsulation is assured.
     * @param workDay The planned work day.
     * @param identification The employees identification number.
     * @return The same as work() on Employee returns. A message with the
     * working time if the command was executed correctly or an error message.
     */
    public String work(WorkDay workDay, int identification) {
        return employees.get(identification).work(workDay);
    }
    
    /**
     * Executes the canWork command on an employee with a given identification.
     * @param workDay The planned work day to be analyzed.
     * @param identification The employees unique identification number.
     * @return True if the employee is allowed to work at that time.
     */
    public boolean canWork(WorkDay workDay, int identification) {
        return employees.get(identification).canWork(workDay);
    }
    
    /**
     * Executes the reasonCantWork command on an employee with a given identification.
     * @param workDay The planned work day that cannot be recorded.
     * @param identification The employees identification number.
     * @return The reason why the planned work day cannot be recorded by the employee.
     */
    public String reasonCantWork(WorkDay workDay, int identification) {
        return employees.get(identification).reasonCantWork(workDay);
    }
    
    /**
     * Returns an ArrayList of shifts that is a deep copy of the actual employees
     * shifts.
     * @param identification The identification number from the employee whose shifts
     * clone will be returned.
     * @return A clone of the employees recorded shifts.
     */
    public ArrayList<Shift> copyOfWorkDays(int identification) {
        return employees.get(identification).copyOfWorkDays();
    }
    
    /**
     * Checks if an employee with the given identification number exists.
     * @param identification The identification number an employee might have.
     * @return True if an there is an employee with the given identification number.
     */
    public boolean containsEmployee(int identification) {
        return employees.containsKey(identification);
    }
    
    /**
     * Overrides clone() to clone an object of this type. Has
     * to iterate over the Map to also clone all registered
     * employees.
     * 
     * Returns a deep copy of the object.
     */
    @Override
    public Employees clone() {
        try {
            Employees e = (Employees) super.clone();
            Map<Integer, Employee> newMap = new HashMap<>();
            
            for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
                newMap.put(entry.getKey(), entry.getValue().clone());
            }
            e.employees = newMap;
            return e;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Creates a clone of the Map containing the companies
     * employees and returns it.
     * 
     * @return A Map that is a clone of the Map containing all
     * of the companies employees.
     */
    public Map<Integer, Employee> getEmployees() {
        
        Map<Integer, Employee> clone = new HashMap<>();
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            clone.put(entry.getKey(), entry.getValue().clone());
        }
        
        return clone;
    }
}