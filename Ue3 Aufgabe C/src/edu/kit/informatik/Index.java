package edu.kit.informatik;

/**
 * Models an index to count.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Index {
    
    /**
     * Index to be counted.
     */
    private final int index;
    
    /**
     * Times the index has been counted.
     */
    private int counter;
    
    /**
     * Constructor for Index.
     * @param index
     */
    public Index(int index) {
        this.index = index;
    }
    
    /**
     * Getter for index.
     * @return index of the Index.
     */
    public int getIndex() {
        return this.index;
    }
    
    /**
     * Getter for counter.
     * @return Number of times index has been counted.
     */
    public int getCounter() {
        return this.counter;
    }
    
    /**
     * Increments counter by one.
     */
    public void incrementCounter() {
        counter++;
    }
}