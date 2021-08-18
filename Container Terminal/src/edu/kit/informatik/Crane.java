package edu.kit.informatik;
import java.util.ArrayList;
import java.util.Collections;

/**
 * With the help of a crane containers can be picked up and placed on a targetpile. It can only transport one
 * container at the same time. Limitations in form of maximal height and maximal weight have to be considered.
 * @author Emilio Rivera
 *
 */
public class Crane {
    private final double maxHeight = 4876.8;  //in cm
    private final int maxWeight = 30000;      //in t
    
    ArrayList<Container> terminalPile = new ArrayList<Container>();
    Container40ft c40ft = new Container40ft();
    Container40ftHC c40ftHC = new Container40ftHC();
    Sector1 s1 = new Sector1();
    ArrayList<Sector1> sector1Array = new ArrayList<Sector1>();
    
    /**
     * validTransport(parameters) checks if the maximal height for the crane isn't going to be surpassed when moving
     * a certain container into the top of a certain pile.
     * @param origin
     * @param target
     * @param startArray
     * @return true if maximal height won't be surpassed.
     */
    public boolean validTransport(int origin, int target, ArrayList<Sector1> startArray) {
        int index = 0;
        double heightIndex = 0;
        int height = 0; 
        
        //Checks height of the container that is going to be repositioned
        for (Sector1 s1 : startArray) {
            if (s1.getPile() == origin) {
                index = startArray.indexOf(s1);
            }
        }
        if (startArray.get(index).getType().equals("40ft") == true) {
            heightIndex = c40ft.getHeight();
        }
        else if (startArray.get(index).getType().equals("40ftHC") == true) {
            heightIndex = c40ftHC.getHeight();
        }
        
         //Checks height of the pile where the container is intended to be repositioned
        for (Sector1 s1 : startArray) {
            if (s1.getPile() == target) {
                if (s1.getType().equals("40ft") == true) {
                    height += c40ft.getHeight();
                }
                else if (s1.getType().equals("40ftHC") == true) {
                    height += c40ftHC.getHeight();
                }
            }
        }
        
        //Checks if maximal height wouldn't be surpassed
        if (height <= maxHeight - heightIndex) {
            return true;
        }
        else 
            return false;
    }
    
    /**
     * Checks if the weight of the container that is intended to be moved doesn't surpass the maximal weight
     * of the crane.
     * @param origin
     * @param startArray
     * @return true if maximal weight won't be surpassed
     */
    public boolean weightAtOrigin(int origin, ArrayList<Sector1> startArray) {
        int index = 0;
        for (Sector1 s1 : startArray) {
            if (s1.getPile() == origin) {
                index = startArray.indexOf(s1);
            }
        }
        if (maxWeight >= startArray.get(index).getWeight()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Gets the index of a searched location from an ArrayList
     * @param location
     * @param startArray
     * @return
     */
    public int getLocation(int location, ArrayList<Sector1> startArray) {
        int index = 0;
        for (Sector1 s1 : startArray) {
            if (s1.getPile() == location) {
                index = startArray.indexOf(s1);
            }
        }
        return index;
    }
    
    /**
     * If weight and height limitations are respected, it moves the container to the new pile it is intended to be on.
     * @param origin
     * @param target
     * @param startArray
     */
    @SuppressWarnings("static-access")
    public void moveContainer(int origin, int target, ArrayList<Sector1> startArray) {
        Collections.sort(startArray, s1.comparingPile);
        if (validTransport(origin, target, startArray) == true) {
            if (weightAtOrigin(origin, startArray) == true) {
                startArray.get(getLocation(origin, startArray)).setPile(target);
            }
        }
        Collections.sort(startArray, s1.comparingPile);
    }
    
    /**
     * Prints the final form of the container-terminal after the realisation of all introduced commands. The output
     * has the form: <Type>;<Id>;<Weight>;<Pile>
     * @param endArray
     */
    public void printContainers(ArrayList<Sector1> endArray) {
        for (Sector1 s1 : endArray) {
            Terminal.printLine(s1.getType() + ";" + s1.getId() + ";" + s1.getWeight() + "kg" + ";" + s1.getPile());
        }
    }
    
    public Crane() {
        
    }
}
