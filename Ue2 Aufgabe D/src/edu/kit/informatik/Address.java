package edu.kit.informatik;

/**
 * Models an address.
 * @author Emilio Rivera
 * @version 1.0
 */
public class Address {
    //Attributes
    private String street;
    private int houseNumber;
    private int postCode;
    private String city;
    private String country;
    
    /**
     * Constructor for an address
     * @param street The streets name
     * @param houseNumber The number of the house
     * @param postCode The postalcode of the zone
     * @param city The city in which the address is in
     * @param country The country of the city
     */
    public Address(String street, int houseNumber, int postCode, String city, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }
    
    /*Airline has to know exact location of each airport. For safety reasons they also need
     * to know the address of their passengers. This is also useful for congratulating frequent
     * travelers on their birthdays and send them information about flight-deals.*/
    public String getStreet() {
        return this.street;
    }
    
    public int getHouseNumber() {
        return this.houseNumber;
    }
    
    public int getPostCode() {
        return this.postCode;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    /*Airports don't change location, but streets sometimes get renamed. If a
     * Client moves to another place the airlines need to be able to update
     * their data.*/
    public void setStreet(String street) {
        this.street = street;
    }
    
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    //Has to be general for clients and airports
    @Override
    public String toString() {
        return street + " " + houseNumber + "\n" + postCode + " " + city + "\n" + country;
    }
}