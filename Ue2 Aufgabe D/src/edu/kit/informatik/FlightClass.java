package edu.kit.informatik;

/**
 * Enum for the available flight classes
 * @author Emilio Rivera
 *
 */
public enum FlightClass {
    /**
     * Economy Class
     */
    ECONOMY {
        //Return the flight class of the passenger
        @Override
        public String toString() {
            return "The Passengers Flight Class is Economy.";
        }
    },
    
    /**
     * Economy Plus Class
     */
    ECONOMYPLUS {
        //Return the flight class of the passenger
        @Override
        public String toString() {
            return "The Passengers Flight Class is Economy Plus.";
        }
    }, 
    
    /**
     * Business Class
     */
    BUSINESS {
        //Return the flight class of the passenger
        @Override
        public String toString() {
            return "The Passengers Flight Class is Business.";
        }
    },
    
    /**
     * First Class
     */
    FIRST {
        //Return the flight class of the passenger
        @Override
        public String toString() {
            return "The Passengers Flight Class is First.";
        }
    }
}
