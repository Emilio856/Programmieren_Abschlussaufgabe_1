package edu.kit.informatik.timeRecordingSystem;

/**
 * Enum containing the possible employee types and their
 * corresponding abbreviations.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public enum EmployeeTypes {
    
    /**
     * A regular employee and its shortening "A".
     */
    A_EMPLOYEE("A"),
    
    /**
     * A production employee and it's shortening "P".
     */
    P_EMPLOYEE("P"),
    
    /**
     * A night employee and it's shortening "N".
     */
    N_EMPLOYEE("N"),
    
    /**
     * A night production employee and it's shortening "NP".
     */
    NP_EMPLOYEE("NP");
    
    /**
     * The shortening of an employee type.
     */
    private String employeeType;
    
    /**
     * Assigns a String as the shortening for the employees type.
     * @param strEmployee The shortening for the employees type
     */
    EmployeeTypes(String strEmployee) {
        this.employeeType = strEmployee;
    }
    
    /**
     * Getter for the employees type shortening.
     * @return The type of employee in form of its abbreviation.
     */
    public String getEmployeeType() {
        return employeeType;
    }
}