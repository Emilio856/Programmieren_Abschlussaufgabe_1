package edu.kit.informatik;

/**
 * Receives a number (n) as a Command Line Argument and calculates
 * the n-th prime number.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    
    /**
     * Calculates the n-th prime number, n being a Command Line Argument.
     * @param args Command Line Argument
     */
    public static void main(String[] args) {
        int nPrime = Integer.parseInt(args[1]);
        int timer = 0;
        int counter = 1;
        int primeNumber = 0;
        boolean notPrime;
        
        while (timer != nPrime) {
            counter++;
            notPrime = false;
            for (int i = 2; i < counter; i++) {
                
                //Checks if counter is a prime number
                if (counter % i == 0) {
                    notPrime = true;
                    break;
                }
            }
            if (!notPrime) {
                primeNumber = counter;
                timer++;
            }
        }
        //n-th prime number reached
        Terminal.printLine(primeNumber);
    }
}