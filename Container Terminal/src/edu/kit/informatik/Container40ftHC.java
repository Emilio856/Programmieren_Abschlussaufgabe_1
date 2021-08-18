package edu.kit.informatik;

/**
 * This is a subclass from Container class. It changes a few of the attributes and
 * overrides methods.
 * @author Emilio Rivera
 *
 */
public class Container40ftHC extends Container {
    
    //Sets type
    @Override
    public String typeOfContainer() {
        type = "40ftHC";
        return type;
    }
    
    //Sets height
    @Override
    public double heightOfContainer() {
        height = 259.08;    //in cm
        return height;
    }
    
    //Getter
    public double getHeight() {
        return heightOfContainer();
    }
    
    /**
     * Constructor for a 40ftHC container.
     * @param id
     * @param weight
     */
    Container40ftHC(int id, int weight) {
        super(id, weight);
        typeOfContainer();
        heightOfContainer();
    }
    
    /**
     * Second constructor in default-form.
     */
    Container40ftHC() {
        
    }
}
