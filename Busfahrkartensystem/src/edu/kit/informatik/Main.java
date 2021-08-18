package edu.kit.informatik;
import edu.kit.informatik.Terminal;
import java.util.ArrayList;

/**
 * Class implements all methods of Commands class and also the Booking, Busline and Client classes. This
 * class runs the program of a bookingsystem for Buscards. Returns errors when invalid commands are 
 * introduced.
 * @author Emilio Rivera
 *
 */
public class Main {
    private static String command;
    
    /**
     * The main method runs all commands for the bookingsystem.
     * @param args
     */
    public static void main(String[] args) {
        Commands myCommand = new Commands();
        
        while (myCommand.getQuit() == false) {
            command = Terminal.readLine();
            String[] input1 = command.split(" ");
            String[] input2 = new String[5];
            
            if (input1[0] != null) {
                if (input1.length == 1) {
                    if (input1[0].equals("list-route")) {
                        myCommand.listRoute();
                    }
                    else if (input1[0].equals("list-bookings")) {
                        myCommand.listBookings();
                    }
                    else if (input1[0].equals("quit")) {
                        myCommand.setQuit(true);
                    }
                    else
                        Terminal.printLine("Error, command is not valid");
                }
                else if (input1.length == 2) {
                    input2 = input1[1].split(";");
                    if (input2.length == 1 && input1[0].equals("remove")) {
                        int input1Int = Integer.parseInt(input2[0]);
                        myCommand.remove(input1Int);
                    }
                    else if (input2.length == 3 && input1[0].equals("book")) {
                        int input1Int = Integer.parseInt(input2[0]);
                        myCommand.book(input1Int, input2[1], input2[2]);
                    }
                    else if (input2.length == 5 && input1[0].equals("add")) {
                        int input1Int = Integer.parseInt(input2[0]);
                        float input4Float = Float.parseFloat(input2[3]);
                        myCommand.add(input1Int, input2[1], input2[2], input4Float, input2[4]);
                    }
                    else 
                        Terminal.printLine("Error, command is not valid");
                }
                else 
                    Terminal.printLine("Error, command is not valid");
            }
            else
                Terminal.printLine("Error, command is not valid");
        }
    }
}
