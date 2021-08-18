package edu.kit.informatik;
import java.util.Arrays;
import java.util.ArrayList;

public class SupportCommands {
    //private final static String separator = "--";
    //private int separatorIndex;
    
    private String type;
    private int id;
    private int weight;
    private int pile;
    
    ArrayList<Integer> allIds = new ArrayList<Integer>();

    
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
    
    public boolean validType(String type) {
        if (type.equals("40ft") || type.equals("40ftHC")) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean validId(int id) {
        if ((id >= 0) && (allIds.contains(id) == false)) {
            allIds.add(id);
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean validWeight(int weight) {
        if (weight > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean validPile(int pile) {
        if (pile >= 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public SupportCommands(String type, int id, int weight, int pile) {
        this.type = type;
        this.id = id;
        this.weight = weight;
        this.pile = pile;
    }
    public SupportCommands() {
        
    }
}
