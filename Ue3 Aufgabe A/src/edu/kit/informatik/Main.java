package edu.kit.informatik;

/**
 * Simulates a Playlist for a radio station.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    /**
     * The input command
     */
    private static String command;
    
    /**
     * Simulates the playlist with all commands
     * @param args Command Line Argument
     */
    public static void main(String args[]) {
        Commands commands = new Commands();
        while (!commands.getQuit()) {
            command = Terminal.readLine();
            String[] input1 = command.split(" ", 2);
            
            if (input1.length == 1 && input1[0] != null) {
                switch (input1[0]) {
                    case "skip":
                        commands.skip();
                        break;
                    case "peek":
                        Terminal.printLine(commands.peek());
                        break;
                    case "list":
                        Terminal.printLine(commands.queue.toString());
                        break;
                    case "history":
                        if (commands.history.history.size() > 0) {
                            Terminal.printLine(commands.history.toString());
                        }
                        break;
                    case "quit":
                        commands.setQuit(true);
                        break;
                    default:
                        Terminal.printError("invalid input");
                }
            }
            else if (input1[0] != null && input1.length == 2) {
                String[] input2 = input1[1].split(":");
                switch (input1[0]) {
                    case "add":
                        if (input2.length == 5 && isNumber(input2[0]) && isNumber(input2[3]) && isNumber(input2[4])) {
                            if (commands.isValidID(Integer.parseInt(input2[0]))
                                    && commands.isValidLength(Integer.parseInt(input2[3]))
                                    && commands.isValidPriority(Integer.parseInt(input2[4]))) {
                                if (!commands.add(Integer.parseInt(input2[0]), input2[1], input2[2],
                                        Integer.parseInt(input2[3]), Integer.parseInt(input2[4]))) {
                                    Terminal.printError("ID already exists!");
                                }
                            }
                            else {
                                Terminal.printError("invalid parameters!");
                            }
                        }
                        else {
                            Terminal.printError("invalid input!");
                        }
                        break;
                    case "remove":
                        if (input2.length == 1 && isNumber(input2[0])) {
                            int removed = commands.remove(Integer.parseInt(input2[0]));
                            if (removed > 0) {
                                Terminal.printLine("Removed " + removed + " songs.");
                            }
                        }
                        else {
                            Terminal.printError("invalid input!");
                        }
                        break;
                    case "play":
                        if (input2.length == 1 && isNumber(input2[0])) {
                            if (commands.isValidLength(Integer.parseInt(input2[0]))) {
                                commands.play(Integer.parseInt(input2[0]));
                            }
                            else {
                                Terminal.printError("length parameter is nor valid!");
                            }
                        }
                        else {
                            Terminal.printError("invalid input!");
                        }
                        break;
                    default:
                        Terminal.printError("invalid input!");
                }
            }
        }
    }
    
    /**
     * Checks if a String is an Integer. Returns true if the String can be
     * converted into an Integer.
     * @param string The String to be analyzed
     * @return True if the String can be converted into an Integer, else false.
     */
    private static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}