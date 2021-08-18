/**
 * Models a traffic light.
 * @author Emilio Rivera
 * @version 1.0
 */
public class TrafficLight {
    /**
     * Attributes
     */
    Light redLight = new Light(false);
    Light greenLight = new Light(false);
    
    /**
     * Initializes a traffic light on a red light.
     */
    public TrafficLight() {
        redLight.lightSwitch();
    }
    
    /**
     * Changes the color of the traffic light from
     * red to green or the other way around.
     */
    public void changeColor() {
        redLight.lightSwitch();
        greenLight.lightSwitch();
    }
    
    /**
     * Checks if a green light is on or not.
     * @return True if the green light is on.
     */
    public boolean isGreen() {
        return greenLight.isLightOn();
    }
}