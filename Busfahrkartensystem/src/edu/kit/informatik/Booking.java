package edu.kit.informatik;
import edu.kit.informatik.*;
import java.util.Comparator;

/**
 * This Booking class contains all attributes and methods needed for a booking
 * @author Emilio Rivera
 *
 */
public class Booking {
    int billNumber = 0;
    private int clientNumber;
    private int busnumber;
    private String firstName;
    private String lastName;
    Client client = new Client(0, null, null);
    
    /**
     * Checks if a String is a valid name or not.
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
     * Constructor for Booking
     * @param clientNumber
     * @param busnumber
     * @param billNumber
     */
    public Booking(int clientNumber, int busnumber, int billNumber) {
        clientNumber = client.getClientNumber();
        this.busnumber = busnumber;
        this.billNumber = getBillNumber();
    }
    
    //Getters & Setters
    public int getClientNumber() {
        return client.getClientNumber();
    }
    public int getBillNumber() {
        return this.billNumber;
    }
    public int getBusnumber() {
        return this.busnumber;
    }
    public void setBillNumber() {
        this.billNumber++;
    }
    public void setBillNumber(int num) {
        this.billNumber = num;
    }
    
    /**
     * Comparator for clientnumbers in ascending order.
     */
    public static Comparator<Booking> clientNumberComparator = new Comparator<Booking>() {
        public int compare(Booking b1, Booking b2) {
            int clientNumber1 = b1.getClientNumber();
            int clientNumber2 = b2.getClientNumber();
            return clientNumber1 - clientNumber2;
        }
    };
    
    /**
     * Comparator for bookingnumbers in descending
     */
    public static Comparator<Booking> bookingNumberComparator = new Comparator<Booking>() {
        public int compare(Booking b1, Booking b2) {
            int billNumber1 = b1.getBillNumber();
            int billNumber2 = b2.getBillNumber();
            return billNumber2 - billNumber1;
        }
    };
}
