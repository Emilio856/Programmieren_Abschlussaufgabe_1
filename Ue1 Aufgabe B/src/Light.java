/**
 * Models a light.
 * @author Emilio Rivera
 * @version 1.0
 */
public class Light {
    private boolean status;
    
    /**
     * Initializes a Light being on or off
     * @param status The light will be on for true,
     * off for false.
     */
    public Light(boolean status) {
        this.status = status;
    }
    
    /**
     * Switches the status of a light from on
     * to off and from off to on.
     */
    public void lightSwitch() {
        status = !status;
    }
    
    /**
     * Checks if a light is on.
     * @return True if the selected light is on.
     */
    public boolean isLightOn() {
        return status == true;
    } 
}