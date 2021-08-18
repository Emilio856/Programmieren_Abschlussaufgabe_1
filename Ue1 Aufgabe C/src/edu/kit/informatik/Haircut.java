package edu.kit.informatik;

/**
 * Models a hair salon with hair stylists and clients on a queue.
 * @author Emilio Rivera
 * @version 1.0
 */
public class Haircut {
    
    /**
     * Calculates which one of the hair stylist will be going to cut 
     * your hair based on the amount of barbers, their cutting time and
     * you place on the queue. 
     * @param args The first number is the amount of hair stylists B at the salon.
     * The second number is your position N on the queue. The rest numbers are
     * the cutting times of each hair stylist M_1, M_2, ..., M_B.     
     */
    public static void main(String[] args) {
        //Max amount of barbers and cutting time
        int[] numberOfStylists = new int[1001];
        int[] cutTime = new int[100001];
        int clientNumber = Integer.parseInt(args[1]);
        
        //Sets Arrays for stylist numbers and cutting times
        for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
            numberOfStylists[i] = i;
        }
        for (int i = 1; i <= args.length - 1; i++) {
            cutTime[i] = Integer.parseInt(args[i]);
        }
        
        //Initializes hair stylists and clients
        HairStylist[] myStylists = new HairStylist[Integer.parseInt(args[0]) + 1];
        Client[] clients = new Client[clientNumber + 1];
        
        //Sets stylists and clients attributes to default
        for (int i = 1; i <= args.length - 2; i++) {
            myStylists[i] = new HairStylist(0, 0, 0);
        }
        for (int i = 1; i <= clients.length - 1; i++) {
            clients[i] = new Client(false, false, 0);
        }
        
        //Configures stylists and clients with the data from args
        for (int i = 1; i <= myStylists.length - 1; i++) {
            myStylists[i].setStylistNumber(numberOfStylists[i]);
        }
        for (int i = 1; i <= args.length - 2; i++) {
            myStylists[i].setCutTime(Integer.parseInt(args[i + 1]));
        }
        for (int i = 1; i <= clients.length - 1; i++) {
            clients[i].setClientNumber(i);
        }
        
        
        while (!clients[clientNumber].getIsDone()) {
            
            //All hair stylists start cutting hair of clients
            for (int i = 1; i < myStylists.length; i++) {
                myStylists[i].cutHair(clients[i]);
                clients[i].satOnChair();
                
                //Checks if a stylist has already finished with a client
                if (myStylists[i].getStylistTimer() == myStylists[i].getCutTime()) {
                    clients[i].haircutDone();
                    myStylists[i].resetTimer();
                    
                    //Searches for the next client on the queue
                    int nextClient;
                    for (int j = 1; j < clients.length; j++) {
                        if (!clients[j].getOnChair()) {
                            nextClient = clients[j].getClientNumber();
                            clients[nextClient].satOnChair();
                            
                            //Checks if you are the next client
                            if (nextClient == clientNumber) {
                                
                                //Prints the number of the hair stylist that will cut your hair
                                int finalStylist = myStylists[i].getStylistNumber();
                                System.out.print(finalStylist);
                                clients[clientNumber].haircutDone();
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        
    }
}