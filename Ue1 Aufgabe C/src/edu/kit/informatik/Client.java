package edu.kit.informatik;

/**
 * Models a client that goes to the hair stylist.
 * @author Emilio Rivera
 * @version 1.0
 */
public class Client {
    //Attributes
    private boolean isDone;
    private boolean onChair;
    private int clientNumber;
    
    /**
     * Constructor for a client
     * @param isDone Shows if the clients haircut is already done.
     * @param onChair Shows if the client is or has already been on
     * the chair for his haircut.
     * @param clientNumber Indicates the number of the client on the queue,
     * starts at 1.
     */
    public Client(boolean isDone, boolean onChair, int clientNumber) {
        this.isDone = isDone;
        this.onChair = onChair;
        this.clientNumber = clientNumber;
    }
    
    /**
     * Signalizes the haircut has been finished.
     */
    public void haircutDone() {
        isDone = true;
    }
    
    /**
     * Signalizes the client is or has been on the chair.
     */
    public void satOnChair() {
        onChair = true;
    }
    
    //Setter
    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    
    //Getters
    public boolean getIsDone() {
        return this.isDone;
    }
    
    public int getClientNumber() {
        return this.clientNumber;
    }
    
    public boolean getOnChair() {
        return this.onChair;
    }
}
