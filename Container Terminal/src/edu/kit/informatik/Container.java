package edu.kit.informatik;

/**
 * Class Container models a Container with it's basic attributes and methods.
 * @author Emilio Rivera
 *
 */
public class Container {
    protected double lenght = 1219.2;   //in cm
    protected double height;
    protected int weight;
    protected int id;
    protected String type;
    
    /**
     * Sets the type of the container.
     * @return Type of the container
     */
    public String typeOfContainer() {
        type = "";
        return type;
    }
    
    /**
     * Sets the height of the container.
     * @return
     */
    public double heightOfContainer() {
        height = 0.0;
        return height;
    }
    
    //Getter
    public int getId() {
        return this.id;
    }
    
    /**
     * Constructor for Container.
     * @param weight
     * @param id
     */
    Container(int weight, int id) {
        this.weight = weight;
        this.id = id;
    }
    
    /**
     * Second constructor in default-form.
     */
    Container() {
        
    }
}
