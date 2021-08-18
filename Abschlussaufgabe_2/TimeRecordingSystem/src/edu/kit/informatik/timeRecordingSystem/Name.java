package edu.kit.informatik.timeRecordingSystem;

/**
 * Models a name containing a first and last name as
 * an immutable object. The toString method is overridden
 * to return the names string representation.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Name {
    
    /**
     * The first name of the name.
     */
    private final String firstName;
    
    /**
     * The last name of the name.
     */
    private final String lastName;
    
    /**
     * Constructor for name. Initializes all the attributes.
     * @param firstName The names first name.
     * @param lastName The names last name.
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Overrides toString to return the first and last name of the
     * employee.
     */
    @Override
    public String toString() {
        return firstName + Main.SPACE_SEPARATOR + lastName;
    }
}