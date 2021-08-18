package edu.kit.informatik;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Stack;
    
/**
 * The class Calculator implements various mathematical opterations
 * and other methods that work with the Stacks functions
 * @author Emilio Rivera
 *
 */
public class Calculator {
    private String element1;
    private String element2;
    private String element3;
    private int elementInt1;
    private int elementInt2;
    private int elementInt3;
    private boolean quit = false;
    
    Stack mainStack = new Stack();
    Stack secondStack = new Stack();
    Stack thirdStack = new Stack();
    
    /**
     * Passes method push(number) for its use in the Main class
     * @param number
     */
    public void push(int number) {
        mainStack.push(number);
    }
    
    /**
     * Passes method pop() for its use in the Main class
     */
    public void pop() {
        mainStack.pop();
    }
    
    /**
     * Passes method peek() for its use in the Main class
     */
    public void peek() {
        mainStack.peek();
        if (mainStack.isEmpty() == true) {
            Terminal.printLine("Error, the Stack is empty");
        }
        else if (mainStack.isEmpty() == false ) {
            Terminal.printLine(mainStack.getPeeked());
        }
    }
    
    /**
     * Adds the first two numbers of the Stacks and deletes them both.
     * Saves the result on the first spot of the Stack. Prints Error if 
     * the Stack has less than two Elements
     */
    public void add() {
        String add1 = mainStack.peek();
        mainStack.pop();
        String add2 = mainStack.peek();
        mainStack.pop();
        
        if (add1 != null && add2 != null) {
            int add1Int = Integer.parseInt(add1);
            int add2Int = Integer.parseInt(add2);
            mainStack.push(add1Int + add2Int);
            Terminal.printLine("OK");
        }
        else if (add1 == null) {
            int add1Int = Integer.parseInt(add1);
            mainStack.push(add1Int);
            Terminal.printLine("Error, Stack has less than two Elements");
        }
        else {
            Terminal.printLine("Error, Stack has less than two Elements");
        }
    }
    
    /**
     * Subtracts the first two numbers of the Stack and deletes them both.
     * Saves the result on the first spot of the Stack. Prints Error if
     * the Stack has less than two Elements
     */
    public void sub() {
        String sub1 = mainStack.peek();
        mainStack.pop();
        String sub2 = mainStack.peek();
        mainStack.pop();
        
        if (sub1 != null && sub2 != null) {
            int sub1Int = Integer.parseInt(sub1);
            int sub2Int = Integer.parseInt(sub2);
            mainStack.push(sub1Int - sub2Int);
            Terminal.printLine("OK");
        }
        else if (sub2 == null) {
            int sub1Int = Integer.parseInt(sub1);
            mainStack.push(sub1Int);
            Terminal.printLine("Error, Stack has less than two Elements");
        }
        else {
            Terminal.printLine("Error, Stack has less than two Elements");
        }
    }
    
    /**
     * Multiplies the first two numbers of the Stack and deletes them both.
     * Saves the result on the first spot of the Stack. Prints Error if
     * the Stack has less than two Elements
     */
    public void multiply() {
        String multiply1 = mainStack.peek();
        mainStack.pop();
        String multiply2 = mainStack.peek();
        mainStack.pop();
        
        if (multiply1 != null && multiply2 != null) {
            int multiply1Int = Integer.parseInt(multiply1);
            int multiply2Int = Integer.parseInt(multiply2);
            mainStack.push(multiply1Int * multiply2Int);
            Terminal.printLine("OK");
        }
        else if (multiply2 == null) {
            int multiply1Int = Integer.parseInt(multiply1);
            mainStack.push(multiply1Int);
            Terminal.printLine("Error, Stack has less than two Elements");
        }
        else {
            Terminal.printLine("Error, Stack has less than two Elements");
        }
    }
    
    /**
     * Divides the first two numbers of the Stack and deletes them both.
     * Saves the result on the first spot of the Stack. Prints Error if
     * the Stack has less than two Elements
     */
    public void divide() {
        String divide1 = mainStack.peek();
        mainStack.pop();
        String divide2 = mainStack.peek();
        mainStack.pop();
        
        if (divide1 != null && divide2 != null) {
            int divide1Int = Integer.parseInt(divide1);
            int divide2Int = Integer.parseInt(divide2);
            mainStack.push(divide1Int / divide2Int);
            Terminal.printLine("OK");
        }
        else if (divide2 == null) {
            int multiply1Int = Integer.parseInt(divide1);
            mainStack.push(multiply1Int);
            Terminal.printLine("Error, Stack has less than two Elements");
        }
        else {
            Terminal.printLine("Error, Stack has less than two Elements");
        }
    }
    
    /**
     * Peeks the first Element of the Stack. If it is a zero it deletes
     * the second Element. Else it deletes the third Element. Prints
     * Error if the Stack has less than three Elements
     */
    public void ifElse() {
        element1 = mainStack.peek();
        mainStack.pop();
        element2 = mainStack.peek();
        mainStack.pop();
        element3 = mainStack.peek();
        mainStack.pop();
        
        if (element1 != null && element2 != null && element3 != null) {
            if (elementInt1 == 0) {
                elementInt1 = Integer.parseInt(element1);
                elementInt2 = Integer.parseInt(element2);
                elementInt3 = Integer.parseInt(element3);
                mainStack.push(elementInt3);
                mainStack.push(elementInt1);
                Terminal.printLine("OK");
            }
            else {
                elementInt1 = Integer.parseInt(element1);
                elementInt2 = Integer.parseInt(element2);
                elementInt3 = Integer.parseInt(element3);
                mainStack.push(elementInt2);
                mainStack.push(elementInt1);
                Terminal.printLine("OK");
            }
        }
        else if (element2 == null) {
            int element1Int = Integer.parseInt(element1);
            mainStack.push(element1Int);
            Terminal.printLine("Error, Stack has less than three Elements");
        }
        else if (element3 == null) {
            int element2Int = Integer.parseInt(element2);
            mainStack.push(element2Int);
            int element1Int = Integer.parseInt(element1);
            mainStack.push(element1Int);
            Terminal.printLine("Error, Stack has less than three Elements");
        }
        else {
            Terminal.printLine("Error, Stack has less than three Elements");
        }
    }
    
    /**
     * Prints the entire Stack. If the Stack is empty it prints an
     * empty line
     */
    public void print() {
        if (mainStack.isEmpty() == true) {
            Terminal.printLine("");
        }
        else if (mainStack.isEmpty() == false) {
            String printElements = "";
            while (mainStack.isEmpty() == false) {
                element1 = mainStack.peek();
                if (printElements.isEmpty() == true) {
                    printElements += element1;
                }
                else if (printElements.isEmpty() == false ) {
                    printElements += "," + element1;
                }
                //printElements += ", " + element1;
                elementInt1 = Integer.parseInt(element1);
                secondStack.push(elementInt1);
                mainStack.pop();
            }
            while (secondStack.isEmpty() == false) {
                element1 = secondStack.peek();
                elementInt1 = Integer.parseInt(element1);
                mainStack.push(elementInt1);
                secondStack.pop();
            }
            Terminal.printLine(printElements);
        }
    }
    
    /**
     * Reverts the order of all Elements of the Stack
     */
    public void revert() {
        if (mainStack.isEmpty() == false) {
            while (mainStack.isEmpty() == false) {
                element1 = mainStack.peek();
                elementInt1 = Integer.parseInt(element1);
                secondStack.push(elementInt1);
                mainStack.pop();
            }
            while (secondStack.isEmpty() == false) {
                element1 = secondStack.peek();
                elementInt1 = Integer.parseInt(element1);
                thirdStack.push(elementInt1);
                secondStack.pop();
            }
            while (thirdStack.isEmpty() == false) {
                element1 = thirdStack.peek();
                elementInt1 = Integer.parseInt(element1);
                mainStack.push(elementInt1);
                thirdStack.pop();
                }
            Terminal.printLine("OK");
        }
        else {
            return;
        }
    }
    
    /**
     * Pops (deletes) all Elements of the Stack
     */
    public void reset() {
        while (mainStack.isEmpty() == false) {
            mainStack.pop();
        }
    }
    
    /**
     * Exits the program
     * @param quit
     */
    public void setQuit(boolean quit) {
        this.quit = quit;
    }
    
    //Getter for quit
    public boolean getQuit() {
        return this.quit;
    }
}
