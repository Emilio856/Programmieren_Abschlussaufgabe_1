package edu.kit.informatik;

/**
 * This is a subclass from Container class. It changes a few of the attributes and
 * overrides methods.
 * @author Emilio Rivera
 *
 */
public class Container40ft extends Container {
    
    //Sets type
    @Override
    public String typeOfContainer() {
        type = "40ft";
        return type;
    }
    
    //Sets height
    @Override
    public double heightOfContainer() {
        height = 289.56;    //in cm
        return height;
    }
    
    //Getter
    public double getHeight() {
        return heightOfContainer();
    }
    
    /**
     * Constructor for a 40ft container.
     * @param id
     * @param weight
     */
    Container40ft(int id, int weight) {
        super(id, weight);
        typeOfContainer();
        heightOfContainer();
    }
    
    /**
     * Second constructor in default-form.
     */
    Container40ft() {
        
    }
}
