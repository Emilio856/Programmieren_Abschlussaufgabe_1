package edu.kit.informatik;

/**
 * Models a hair stylist.
 * @author Emilio Rivera
 * @version 1.0
 */
public class HairStylist {
    //Attributes
    private int cutTime;
    private int stylistNumber;
    private int stylistTimer;
    
    /**
     * Constructor for a hair stylist.
     * @param cutTime The time the hair stylist needs to finish a haircut.
     * @param stylistNumber The number of the hair Stylist. Starts at 1
     * @param stylistTimer The timer that shows for how long the hair stylist
     * has been cutting a clients hair.
     */
    public HairStylist(int cutTime, int stylistNumber, int stylistTimer) {
        this.cutTime = cutTime;
        this.stylistNumber = stylistNumber;
        this.stylistTimer = stylistTimer;
    }
    
    /**
     * Cuts the hair of a client for a fraction of the total
     * necessary time that the stylist needs.
     * @param clients The client whose hair is being cut.
     */
    public void cutHair(Client clients) {
        stylistTimer++;
    }
    
    /**
     * Sets the timer of the stylist to 0.
     */
    public void resetTimer() {
        stylistTimer = 0;
    }
    
    //Setters
    public void setStylistNumber(int newStylistNumber) {
        this.stylistNumber = newStylistNumber;
    }
    
    public void setCutTime(int newCutTime) {
        this.cutTime = newCutTime;
    }
    
    //Getters
    public int getCutTime() {
        return this.cutTime;
    }
    
    public int getStylistTimer() {
        return this.stylistTimer;
    }
    
    public int getStylistNumber() {
        return this.stylistNumber;
    }
}