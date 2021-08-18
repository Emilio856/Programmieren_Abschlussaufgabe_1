package edu.kit.informatik;
import edu.kit.informatik.*;

/**
 * Class Client contains all  attributes and parameters for a client.
 * @author Emilio Rivera
 *
 */
public class Client {
    private int clientNumber = 0;
    private String firstName;
    private String lastName;
    
    /**
     * Checks if a name is valid or not.
     * @param name
     * @return
     */
    public boolean validName(String name) {
        if (name != null) {
            return true;
        }
        else 
            return false;
    }
    
    /**
     * Constructor for Client
     * @param clientNumber
     * @param firstName
     * @param lastName
     */
    public Client(int clientNumber, String firstName, String lastName) {
        this.clientNumber = clientNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    //Getters & Setters
    public int getClientNumber() {
        return this.clientNumber;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setClientNumber() {
        this.clientNumber++;
    }
    public void setClientNumber(int num) {
        this.clientNumber = num;
    }
}
