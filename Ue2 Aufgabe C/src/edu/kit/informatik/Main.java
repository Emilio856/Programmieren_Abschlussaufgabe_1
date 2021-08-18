package edu.kit.informatik;

/**
 * Executes a program to check if a sentence is holoalphabetic.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    private static String command;

    /**
     * Check if a sentence is an isogram, a pangram or else.
     * @param args Command Line Argument
     */
    public static void main(String[] args) {
        
        Holoalphabetic holo = new Holoalphabetic();

        //While quit == false
        while (!holo.getQuit()) {
            
            command = Terminal.readLine();
            String[] input = command.split(" ");
            String sentence = "";
            
            //Forms a new String without whitespace
            for (int i = 1; i < input.length; i++) {
                sentence = sentence + input[i];
            }
            
            if (input[0].equals("holoalphabetic?")) {
                holo.reset();
                holo.letterCounter(sentence);
                if (holo.isPangram() && !holo.isIsogram()) {
                    Terminal.printLine("pangram");
                }
                else if (holo.isIsogram() == true) {
                    Terminal.printLine("isogram");
                }
                else {
                    Terminal.printLine("false");
                }
            }
            
            //Quit Program
            else if (input[0].equals("quit")) {
                holo.setQuit(true);
            }
        }
    }
}