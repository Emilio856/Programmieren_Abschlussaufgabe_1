package edu.kit.informatik;
import edu.kit.informatik.*;

/**
 * Class Busline contains all  attributes and parameters for a busline. 
 * @author Emilio Rivera
 *
 */
public class Busline {
    private int busnumber;
    private String startStation;
    private String endStation;
    private float price;
    private String currency;
    
    /**
     * Checks if a busnumber is valid or not.
     * @param busnumber
     * @return
     */
    public boolean validBusnumber(int busnumber) {
        if (busnumber > 0 && busnumber < 1000) {
            return true;
        }
        else
            return false;
    }
    
    /**
     * Checks if a busstation is valid or not.
     * @param station
     * @return
     */
    public boolean validStation(String station) {
        if (station != null) {
            return true;
        }
        else 
            return false;
    }
    
    /**
     * Checks if a price is valid or not
     * @param price
     * @return
     */
    public boolean validPrice(float price) {
        if (price > 0) {
            String priceString = Float.toString(price);
            String[] inputPrice = priceString.split(".");
            if (inputPrice.length == 2 && inputPrice[1].length() == 2) {
                return true;
            }
            return true;
        }
        else
            return false;
    }
    
    /**
     * Checks if a currency is valid or not
     * @param currency
     * @return
     */
    public boolean validCurrency(String currency) {
        if (currency.equals("EUR") || currency.equals("USD") || currency.equals("JPY")) {
            return true;
        }
        else 
            return false;
    }
    
    /**
     * Constructor for Busline
     * @param busnumber
     * @param startStation
     * @param endStation
     * @param price
     * @param currency
     */
    public Busline(int busnumber, String startStation, String endStation, float price, String currency) {
        this.busnumber = busnumber;
        this.startStation = startStation;
        this.endStation = endStation;
        this.price = price;
        this.currency = currency;
    }
    
    //Getters & Setters
    public int getBusnumber() {
        return this.busnumber;
    }
    public String getStartStation() {
        return this.startStation;
    }
    public String getEndStation() {
        return this.endStation;
    }
    public float getPrice() {
        return this.price;
    }
    public String getCurrency() {
        return this.currency;
    }
}
