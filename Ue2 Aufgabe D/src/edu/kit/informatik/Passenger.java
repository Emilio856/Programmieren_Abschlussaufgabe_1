package edu.kit.informatik;

/**
 * Models a passenger
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Passenger {
    //Attributes
    private String name = "";
    private Date birthday;
    private Address address;
    private FlightClass flightClass;
    
    /**
     * Constructor for a passenger
     * @param name Name of the passenger
     * @param birthday birth date of the passenger
     * @param address Address of the passenger
     * @param flightClass Flight Class of the passenger
     */
    public Passenger(String name, Date birthday, Address address, FlightClass flightClass) {
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.flightClass = flightClass;
    }
    
    //For safety reasons the airline has to know the name of the passenger
    public String getName() {
        return this.name;
    }
    
    //The Airline can see which Flight Class the client bought
    public FlightClass getFlightClass() {
        return this.flightClass;
    }
    
    //In case of overbooking or in case the passenger decides to buy an upgrade
    //the airline can change his Flight Class. 
    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }
    
    /*Most of the times when looking for a clients information it is because he/she booked
     * a flight. In this case his/her birthday is irrelevant for his trip data. Birthday and
     * address can be get with the getters. In an automated system to send congratulation and
     * information letters you can just send the card if client.birthday.getDay/Month == currentDay*/
    @Override
    public String toString() {
        return "The passenger " + "is flying in " + flightClass + " class";
    }
}