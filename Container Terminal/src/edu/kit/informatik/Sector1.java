package edu.kit.informatik;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Sector1 is the first part of an introduced textfile on the command line argument.
 * @author Emilio Rivera
 *
 */
public class Sector1 {
    private String type;
    private int id;
    private int weight;
    private int pile;

    ArrayList<Integer> allIds = new ArrayList<Integer>();
    
    /**
     * Checks if the array of strings is build in a valid form for this program.
     * @param s1
     * @return true if array can be used for the container terminal
     */
    public boolean validArray(ArrayList<Sector1> s1) {
        for (Sector1 a : s1) {
            if ((validType(a.getType()) == true) && (validId(a.getId()) == true)
                    && (validWeight(a.getWeight()) == true) && (validPile(a.getPile()) == true)) {
                continue;
            }
            else
                return false;
        }
        return true;
    }
    
    /**
     * Checks if every string on the array can be split into the needed amount of strings
     * @param array
     * @return true if every string can be split into four parts
     */
    public boolean separableSector1(String[] array) {
        for (String str : array) {
            String[] helpArray = str.split(";");
            if (helpArray.length == 4) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the type is a valid string
     * @param type
     * @return
     */
    public boolean validType(String type) {
        if (type.equals("40ft") || type.equals("40ftHC")) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if the id is a valid integer
     * @param id
     * @return
     */
    public boolean validId(int id) {
        if ((id >= 0) && (allIds.contains(id) == false)) {
            allIds.add(id);
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if the weight is a valid integer
     * @param weight
     * @return
     */
    public boolean validWeight(int weight) {
        if (weight > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if the pile is a valid integer
     * @param pile
     * @return
     */
    public boolean validPile(int pile) {
        if (pile >= 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Sorts containers in ascending order of the piles
     */
    public static Comparator<Sector1> comparingPile = new Comparator<Sector1>() {
        public int compare(Sector1 s1, Sector1 s2) {
            int pileNumber1 = s1.getPile();
            int pileNumber2 = s2.getPile();
            return pileNumber1 - pileNumber2;
        }
    };
    
    //Getters & Setters
    public String getType() {
        return this.type;
    }
    public int getId() {
        return this.id;
    }
    public int getWeight() {
        return this.weight;
    }
    public int getPile() {
        return this.pile;
    }
    public void setPile(int newPile) {
        this.pile = newPile;
    }
    
    /**
     * Constructor for Sector1
     * @param type
     * @param id
     * @param weight
     * @param pile
     */
    public Sector1(String type, int id, int weight, int pile) {
        this.type = type;
        this.id = id;
        this.weight = weight;
        this.pile = pile;
    }
    
    /**
     * Second constructor in a default-form
     */
    public Sector1() {
        
    }
}
