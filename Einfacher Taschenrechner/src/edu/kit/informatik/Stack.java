package edu.kit.informatik;

/**
 * The class Stack defines a Stack and its three basic functions: push,
 * peek, pop. It also implements a method to check if the Stack is empty.
 * @author Emilio Rivera
 *
 */
class Stack {
    private String peeked;
    
    private class ListCell {
        String number;
        ListCell next;
    }
    
    ListCell first;
    public Object calculator;
    
    Stack() {    //empty Stack
        this.first = null;
    }
    
    /**
     * Saves a number on the top spot of the Stack
     * @param number number that is saved
     */
    public void push(int number) {
        ListCell listCell = new ListCell();
        listCell.number = Integer.toString(number);
        listCell.next = first;
        first = listCell;
        return;
    }
    
    /**
     * Deletes the first Element of the Stack and puts the next one 
     * on the first spot
     */
    public void pop() {
        if (isEmpty() == true) {
            return;
        }
        else if (isEmpty() == false) {
            first = first.next;
            return;
        }
    }
    
    /**
     * Prints the first number of the Stack
     * @return String representation of the first Element of the Stack
     */
    public String peek() {
        if (isEmpty() == false) {
            peeked = first.number;
        }
        else {
            return null;
        }
        return peeked;
    }
    
    //Getter 
    public String getPeeked() {
        return peeked;
    }
    
    /**
     * Checks if the Stack is currently empty or not
     * @return state of the Stack
     */
    public boolean isEmpty() {
        return first == null;
    }
}
