package edu.kit.informatik;

/**
 * Models an Airport 
 * @author Emilio Rivera
 * @version 1.0
 * 
 */
public class Airport {
    //Attributes
    private String name;
    private Address address;
    
    /**
     * Constructor for an airport
     * @param name Name of the airport
     * @param address Address of the airport
     */
    public Airport(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    
    //Airline can always see the name of the airport
    public String getName() {
        return this.name;
    }
    
    //Sometimes airports change name so it could be useful to have a setter
    //for that (i.e. Alexander Pushkin in Moscow)
    public void setName(String name) {
        this.name = name;
    }
    
    //Display the name of the airport and the address for contact purposes
    @Override
    public String toString() {
        return name + "\n" + address.toString();
    }
}