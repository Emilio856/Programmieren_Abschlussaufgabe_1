package edu.kit.informatik;
import java.util.ArrayList;

/**
 * Secto2 is the second part of an introduced textfile on the command line argument
 * @author Emilio Rivera
 *
 */
public class Sector2 {
    private int origin;
    private int target;
    
    /**
     * Checks if every string on the array can be split into the needed amount of strings
     * @param array
     * @return true if every string can be split into two parts
     */
    public boolean separableSector2(String[] array) {
        for (String str : array) {
            String[] helpArray = str.split(";");
            if (helpArray.length == 2) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the array of strings is build in a valid form for this program.
     * @param s2
     * @return true if array can be used for the container terminal
     */
    public boolean validArray(ArrayList<Sector2> s2) {
        for (Sector2 a : s2) {
            if (validRoute(a.getOrigin(), a.getTarget()) == true) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the origin pile and the target pile are valid integers
     * @param origin
     * @param target
     * @return
     */
    public boolean validRoute(int origin, int target) {
        if ((origin >= 0) && (target >= 0) && (origin - target != 0)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //Getters
    public int getOrigin() {
        return this.origin;
    }
    public int getTarget() {
        return this.target;
    }
    
    /**
     * Constructor for Sector2
     * @param origin
     * @param target
     */
    public Sector2(int origin, int target) {
        this.origin = origin;
        this.target = target;
    }
    
    /**
     * Second constructor in a default-form
     */
    public Sector2() {
        
    }
}
