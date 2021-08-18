package edu.kit.informatik;

/**
 * Models a Flight
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Flight {
    //Attributes
    private static int idCounter = 0;
    private Airport departureAirport;
    private Airport destinationAirport;
    private int iD;
    private Date departureDate;
    private int flightDuration;
    private Passenger[] passengers = new Passenger[100];
    private Plane plane;
    
    /**
     * Constructor for a flight
     * @param departureAirport The departure airport
     * @param destinationAirport The destination airport
     * @param departureDate The departure date
     * @param flightDuration The flights duration
     * @param passengers The passengers in the flight
     * @param plane The plane for the fligth
     */
    public Flight(Airport departureAirport, Airport destinationAirport, Date departureDate,
            int flightDuration, Passenger[] passengers, Plane plane) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.iD = idCounter;
        idCounter++;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
        this.passengers = passengers;
        this.plane = plane;
    }
    
    //It's important to know the flightNumber
    public int getId() {
        return this.iD;
    }
    
    //It is very important to know when the plane is going to depart
    public String getDate() {
        return this.departureDate.toString();
    }
    
    //The Airline knows how long the flight will be
    public int getFlightDuration() {
        return this.flightDuration;
    }
    
    //Gives the most important information about the flight without revealing personal information
    @Override
    public String toString() {
        return "Flight: " + iD + "\n" + departureAirport.getName() + " - " + destinationAirport.getName()
                + "\n" + "Departs on the" + departureDate.toString() + "\n" + "Duration: " + flightDuration
                + "\n" + "Plane data: " + plane.getBrandName() + " " + plane.getSerialNumber();
    } 
}