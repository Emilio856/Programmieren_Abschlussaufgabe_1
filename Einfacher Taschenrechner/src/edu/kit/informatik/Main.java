package edu.kit.informatik;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Stack;

public class Main {
    static int nInt;
    int add1;
    int add2;
    Stack mainStack = new Stack();
    Stack secondStack = new Stack();
    Calculator calculator = new Calculator();
    private static String command;
    
    /**
     * Checks if the input is a number. Returns true for number.
     * Return false if it's not a number
     * @param string
     * @return
     */
    public static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            int isNumber = Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Runs all functions of the calculator. Checks if inputs correspond with 
     * implemented methods. Prints error warnings or success messages according
     * to given input
     * @param args
     */
    public static void main(String[] args) {
        
        Calculator calculator = new Calculator();
        
        while (calculator.getQuit() == false) {
            command = Terminal.readLine();
            String[] inputCommand = command.split(" ");
            
            if (inputCommand[0] != null && inputCommand.length == 2) {
                
                if (inputCommand[0].equals("push")) {
                    
                    if (inputCommand[1] != null && isNumber(inputCommand[1]) == true) {
                        nInt = Integer.parseInt(inputCommand[1]);
                        calculator.push(nInt);
                        Terminal.printLine("OK");               //runs "push(number)"
                    }
                    else if (isNumber(inputCommand[1]) == false) {  //returns error message
                        Terminal.printLine("Error, symbol after push is not a number");
                    }
                }
            }
            else if (inputCommand[0] != null && inputCommand.length == 1) {
                if (inputCommand[0].equals("pop")) {            //runs "pop()"
                    calculator.pop();
                    Terminal.printLine("OK");
                }
                else if (inputCommand[0].equals("peek")) {      //runs "peek()"
                    calculator.peek();
                }
                else if (inputCommand[0].equals("add")) {       //runs "add()"
                    calculator.add();
                }
                else if (inputCommand[0].equals("sub")) {       //runs "sub()"
                    calculator.sub();
                }
                else if (inputCommand[0].equals("multiply")) {  //runs "multiply()"
                    calculator.multiply();
                }
                else if (inputCommand[0].equals("divide")) {    //runs "divide()"
                    calculator.divide();
                }
                else if (inputCommand[0].equals("if-else")) {   //runs "ifElse()"
                    calculator.ifElse();
                }
                else if (inputCommand[0].equals("print")) {     //runs "print()"
                    calculator.print();
                }
                else if (inputCommand[0].equals("revert")) {    //runs "revert()"
                    calculator.revert();
                }
                else if (inputCommand[0].equals("reset")) {     //runs "reset()"
                    calculator.reset();
                }
                else if (inputCommand[0].equals("quit")) {      //runs "setQuit()"
                    calculator.setQuit(true);
                }
                else {                                          //returns error message
                    Terminal.printLine("Error, invalid Command");
                }
            }
            else {                                              //return error message
                Terminal.printLine("Error, invalid number of Arguments");
            }
        }
    }
}
