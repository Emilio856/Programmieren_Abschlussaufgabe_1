package edu.kit.informatik;

/**
 * Models a planet that will be visited by the bounty hunter.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Planet {
    
    /**
     * Assigns a planet its new index.
     */
    private static int newIndex;
    
    /**
     * True if the planet has been visited, else false.
     */
    private boolean visit;
    
    /**
     * Index of the planet. Starts at 0.
     */
    private final int index;
    
    /**
     * Index of the next planet.
     */
    private final int next;
    
    /**
     * True, if the planet is followed by another.
     */
    private final boolean hasNext;
    
    /**
     * True, if planet is last in the command.
     */
    private boolean last;
    
    /**
     * Constructor for Planet
     * @param hasNext True if Planet has a connection to another
     * another Planet in the Hyperspace
     * @param next Index of the next Planet
     */
    public Planet(boolean hasNext, int next) {   
        this.hasNext = hasNext;
        this.next = next;
        index = newIndex;
        addIndex();
        this.visit = false;
    }
    
    /**
     * Constructor for Planet.
     * @param next Index of the next planet.
     * @param last True, if planet is last in command.
     */
    public Planet(int next, boolean last) {
        index = newIndex;
        this.visit = false;
        this.hasNext = true;
        this.next = next;
        if (last == true) {
            addIndex();
        }
    }
    
    /**
     * Getter for visit.
     * @return True, if planet has been visited
     */
    public boolean getVisit() {
        return this.visit;
    }
    
    /**
     * Getter for index.
     * @return Index of the planet.
     */
    public int getIndex() {
        return this.index;
    }
    
    /**
     * Getter for next.
     * @return Index of the next planet.
     */
    public int getNext() {
        return this.next;
    }
    
    /**
     * Getter for hasNext.
     * @return True, if planet has a next planet in list.
     */
    public boolean getHasNext() {
        return this.hasNext;
    }
    
    /**
     * Increments newIndex by one.
     */
    public void addIndex() {
        newIndex++;
    }
    
    /**
     * Indicates the planet it has been visited.
     */
    public void visitedPlanet() {
        this.visit = true;
    }
}