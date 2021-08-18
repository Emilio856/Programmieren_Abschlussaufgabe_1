package edu.kit.informatik;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.lang.Object;
import edu.kit.informatik.*;

/**
 * This class contains all commands that can be applied with the Booking, Busline and Client classes
 * @author Emilio Rivera
 *
 */
public class Commands {
    private boolean quit = false;
    
    Busline busline = new Busline(0, null, null, 0, null);
    Busline[] allBuslines = new Busline[1000];
    
    ArrayList<Booking> allBookings = new ArrayList<Booking>();
    Booking booking = new Booking(0, 0, 0);
    
    ArrayList<Client> allClients = new ArrayList<Client>();
    Client client = new Client(0, null, null);
    
    /**
     * The "add" method adds a new Busline if the requested parameters contain correct values and prints "OK". Else
     * it prints an error.
     * @param busnumber
     * @param startStation
     * @param endStation
     * @param price
     * @param currency
     */
    public void add(int busnumber, String startStation, String endStation, float price, String currency) {
        if (busline.validBusnumber(busnumber) == true) {
            if (busline.validStation(startStation) == true && busline.validStation(endStation) == true) {
                if (busline.validPrice(price) == true) {
                    if (busline.validCurrency(currency) == true) {
                        if (containsBusnumber(busnumber) == false) {
                            float round = (float) (((int) (price * 100)) / 100.00);
                            price = round;
                            allBuslines[busnumber] = new Busline(busnumber, startStation, endStation, price, currency);
                            Terminal.printLine("OK");
                        }
                        else
                            Terminal.printLine("Error, a busline with this number already exists");
                    }
                    else
                        Terminal.printLine("Error, currency is not valid");
                }
                else 
                Terminal.printLine("Error, price is not valid");
            }
            else
                Terminal.printLine("Error, Startstation and/or Endstation is not valid");
        }
        else 
            Terminal.printLine("Error, Busnumber is not valid");
    }
    
    /**
     * The "remove" method removes a Busline from the booking system if this Busline exists. Else it prints
     * an error.
     * @param busnumber
     */
    public void remove(int busnumber) {
        if (busline.validBusnumber(busnumber) == true) {
            if (containsBusnumber(busnumber) == true) {
                allBuslines[busnumber] =  null;
                Terminal.printLine("OK");
            }
            else
                Terminal.printLine("Error, there isn't a Busline with this Busnumber");
        }
        else
            Terminal.printLine("Error, this is not a valid number for a Busline");
    }
    
    /**
     * The "listRoute" method prints all existing buslines. They are sorted by ascending busnumber.
     */
    public void listRoute() {
        for (int i = 0; i < 1000; i -= -1) {
            if (allBuslines[i] != null) {
                Terminal.printLine(allBuslines[i].getBusnumber() + ";" + allBuslines[i].getStartStation() + ";"
                        + allBuslines[i].getEndStation() + ";" + allBuslines[i].getPrice() + ";"
                        + allBuslines[i].getCurrency());
            }
            else 
                continue;
        }
    }
    
    /**
     * The "book" method allows a client to book a ticket. Every booking gets a bookingnumber. New clients also get
     * a new clientnumber. It prints the bookingnumber and clientnumber.
     * @param busnumber
     * @param firstName
     * @param lastName
     */
    public void book(int busnumber, String firstName, String lastName) {
        if (busline.validBusnumber(busnumber) == true) {
            if (client.validName(firstName) == true && client.validName(lastName) == true) {
                if (containsBusnumber(busnumber) == true) {
                    
                    //Client doesn't exist yet
                    if (containsName(firstName, lastName) == false) {
                        Client c = new Client(0, firstName, lastName);
                        allClients.add(c);
                                            
                        booking.setBillNumber();
                        client.setClientNumber();
                        c.setClientNumber(client.getClientNumber());
                        Booking e = new Booking(c.getClientNumber(), busnumber, booking.getBillNumber());
                        allBookings.add(e);
                        e.client.setClientNumber(client.getClientNumber());
                        e.setBillNumber(booking.getBillNumber());
                        Terminal.printLine(booking.getBillNumber() + ";" + c.getClientNumber());
                    }
                    
                    //Client already exists
                    else if (containsName(firstName, lastName) == true) {
                        Client c = new Client(0, firstName, lastName);
                        
                        booking.setBillNumber();
                        int aClientNumber;
                        aClientNumber = getIndex(firstName, lastName);
                        c.setClientNumber(aClientNumber);
                        Booking e = new Booking(c.getClientNumber(), busnumber, booking.getBillNumber());
                        allBookings.add(e);
                        e.client.setClientNumber(aClientNumber);
                        e.setBillNumber(booking.getBillNumber());
                        Terminal.printLine(booking.getBillNumber() + ";" + c.getClientNumber());
                    }
                }
                else
                    Terminal.printLine("Error, a Busline with this Busnumber does not exist");
            }
            else
                Terminal.printLine("Error, first Name and/or last Name is not valid");
        }
        else
            Terminal.printLine("Error, Busnumber is not valid");
    }
    
    /**
     * The supportmethod "containsName" checks, if a client already exists or not.
     * @param firstName
     * @param lastName
     * @return
     */
    public boolean containsName(String firstName, String lastName) {
        for (Client client : allClients) {
            if (client.getFirstName().contains(firstName) == true && client.getLastName().contains(lastName) == true) 
                return true;
        }
        return false;
    }
    
    /**
     * The supportmethod "getIndex" returns the index of a client with matching first and last Name.
     * @param firstName
     * @param lastName
     * @return Index
     */
    public int getIndex(String firstName, String lastName) {
        int myNumber = 0;
        for (Client client : allClients) {
            if (client.getFirstName().contains(firstName) == true && client.getLastName().contains(lastName) == true) {
                myNumber = client.getClientNumber();
            }
        }
        return myNumber;
    }
    
    /**
     * Checks if a busnumber has been added to the buslines
     * @param busnumber
     * @return true if the busline has already been added
     */
    public boolean containsBusnumber(int busnumber) {
        boolean result = false;
        for (int i = 0; i < allBuslines.length; i++) {
            if (allBuslines[i] != null) {
                if (allBuslines[i].getBusnumber() == busnumber) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * The "listBookings" method prints all bookings made. Bookings for deleted Buslines are excluded. The bookings
     * are printed by ascending clientnumber. If a client has more than one booking, they are printed in descending
     * order of the bookingnumber.
     * If no bookings have been made, there is no output.
     */
    public void listBookings() {
        Collections.sort(allBookings, booking.clientNumberComparator.thenComparing(booking.bookingNumberComparator));
        for (Booking b : allBookings) {
            if (containsBusnumber(b.getBusnumber()) == true) {
                Terminal.printLine(b.client.getClientNumber() + ";" + b.getBusnumber()
                        + ";" + b.getBillNumber());
            }
        }
    }
    
    //Setter for quit
    public void setQuit(boolean quit) {
        this.quit = quit;
    }
    
    //Getter for quit
    public boolean getQuit() {
        return this.quit;
    }
}
