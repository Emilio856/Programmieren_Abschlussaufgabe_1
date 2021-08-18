/**
 * Models a Crossing containing four traffic lights.
 * @author Emilio Rivera
 * @version 1.0
 */
public class Crossing {
    /**
     * Attributes
     */
    TrafficLight[] trafficLight = new TrafficLight[4];
    
    /**
     * Initializes a Crossing with traffic lights 0 and 1 
     * on a green status
     */
    public Crossing() {
        trafficLight[0].changeColor();
        trafficLight[1].changeColor();
    }
    
    /**
     * Changes the color of all traffic lights in a crossing.
     */
    public void switchTrafficLight() {
        trafficLight[0].changeColor();
        trafficLight[1].changeColor();
        trafficLight[2].changeColor();
        trafficLight[3].changeColor();
    }
    
    /**
     * Checks if it is safe to cross a given traffic light.
     * @param number The number from 0 to 3 of the traffic
     * light to cross.
     * @return True if the traffic light with the specified number
     * is currently green.
     */
    public boolean isSafe(int number) {
        return trafficLight[number].isGreen();   
    }
}