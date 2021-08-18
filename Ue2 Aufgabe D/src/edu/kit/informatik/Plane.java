package edu.kit.informatik;

import java.util.Objects;

/**
 * Models a plane
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Plane {
    //Attributes
    private String brandName;
    private int serialNumber;
    private Airport homeAirport;
    
    /**
     * Constructor for a plane
     * @param brandName Name of the planes brand
     * @param serialNumber Serial number of the plane
     * @param homeAirport Home Airport of the plane
     */
    public Plane(String brandName, int serialNumber, Airport homeAirport) {
        this.brandName = brandName;
        this.serialNumber = serialNumber;
        this.homeAirport = homeAirport;
    }
    
    //Necessary to compare planes
    public String getBrandName() {
        return this.brandName;
    }
    
    //Necessary to compare planes
    public int getSerialNumber() {
        return this.serialNumber;
    }
    
    //Overriding equals() to compare two planes
    @Override
    public boolean equals(Object aPlane) {
        //True if object is compared with itself
        if (aPlane == this) {
            return true;
        }
        
        //Checks null
        if (aPlane == null) {
            return false;
        }
        
        //Checks type + cast
        if (!(aPlane instanceof Plane)) {
            return false;
        }
        Plane plane = (Plane) aPlane;
        
        return Integer.compare(serialNumber, plane.serialNumber) == 0
                && Objects.equals(brandName, plane.brandName);
    }
    
    //Give all the information about the plane
    @Override
    public String toString() {
        return "The planes brand and serial number are: " + brandName + ", " + serialNumber
                + ". It's home airport is the " + homeAirport + " airport.";
    }
}