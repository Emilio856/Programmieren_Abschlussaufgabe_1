package edu.kit.informatik;

/**
 * Main method for a class editor
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    
    /**
     * Simulates a class editor. Can add constructs of the types: class, enum, interface.
     * Can add inheritance and implementations between these structures. Also allows to
     * add attributes and methods to the constructs. Has various methods to print constructs,
     * attributes and methods in different ways. Returns errors if the entered input is not
     * correct.
     * @param args Command-Line Arguments
     */
    public static void main(String[] args) {
        Commands command = new Commands();
        
        while (!command.getQuit()) {
            String[] input1 = Terminal.readLine().split(" ", 2);
            if (input1.length == 1 && input1[0].equals("list-constructs")) {
                Terminal.printLine(command.listConstructs());
            }
            else if (input1.length == 1 && input1[0].equals("quit")) {
                command.setQuit(true);
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 2 && input1[0].equals("add-construct")) {
                Terminal.printLine(command.addConstruct(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 2 && input1[0].equals("add-extends")) {
                Terminal.printLine(command.addExtends(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 2 && input1[0].equals("add-implements")) {
                Terminal.printLine(command.addImplements(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split("::").length == 2 && input1[0].equals("add-attribute")) {
                Terminal.printLine(command.addAttribute(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split("::").length == 2 && input1[0].equals("add-method")) {
                Terminal.printLine(command.addMethod(input1[1]));
            }
            else if (input1.length == 1 && input1[0].equals("list-constructs")) {
                Terminal.printLine(command.listConstructs());
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 1 && input1[0].equals("list-attributes")) {
                Terminal.printLine(command.listAttributes(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 1 && input1[0].equals("list-methods")) {
                Terminal.printLine(command.listMethods(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split("::").length == 2
                    && input1[0].equals("find-method-by-name")) {
                Terminal.printLine(command.findMethodByName(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 1
                    && input1[0].equals("list-all-attributes")) {
                Terminal.printLine(command.listAllAttributes(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 1
                    && input1[0].equals("list-shadowing-attributes")) {
                Terminal.printLine(command.listShadowingAttributes(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split(" ").length == 1 && input1[0].equals("list-all-methods")) {
                Terminal.printLine(command.listAllMethods(input1[1]));
            }
            else if (input1.length == 2 && input1[1].split("::").length == 2
                    && input1[0].equals("find-method-override")) {
                Terminal.printLine(command.findMethodOverride(input1[1]));
            }
            else {
                Terminal.printError("invalid input!");
            }
        }
    }
}