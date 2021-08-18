package edu.kit.informatik;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Runs all commands from the other classes. Recieves a textfile as input and outputs the changed order of the
 * container terminal after all changes have been made.
 * @author Emilio Rivera
 *
 */
public class Main {
    private static String[] path;
    static Crane terminalCrane = new Crane();
    //static String file = "C:\\Users\\emili\\Desktop\\Programmieren\\test1.txt";
    static Sector1 mySector1 = new Sector1();
    static Sector2 mySector2 = new Sector2();
    static ArrayList<Sector1> arraySector1 = new ArrayList<Sector1>();
    static ArrayList<Sector2> arraySector2 = new ArrayList<Sector2>();
    static ArrayList<Sector1> arrayOutput = new ArrayList<Sector1>();
    
    /**
     * Runs the container terminal based on a textfile that has to be introduced
     * @param args
     */
    public static void main(String[] args) {   
        String myFile = args[0];
        path = Terminal.readFile(myFile);
        String[] sector1;
        String[] sector2;
        String[] sector1split;
        String[] sector2split;
        
        //Checks that the file has the minimum amount of lines
        if (path.length >= 3) {
            
            //Checks if the file contains a first and a second sector. Separetes them
            if ((Arrays.asList(path).indexOf("--") >= 1)
                    && (Arrays.asList(path).indexOf("--") < path.length - 1)) {
                sector1 = Arrays.copyOfRange(path, 0, Arrays.asList(path).indexOf("--"));
                sector2 = Arrays.copyOfRange(path, Arrays.asList(path).indexOf("--") + 1, path.length);
                
                //Checks for the rest of requirements
                if ((mySector1.separableSector1(sector1) == true) && (mySector2.separableSector2(sector2) == true)) {
                    
                    //Splits sector one
                    for (String str : sector1) {
                        sector1split = str.split(";");
                        int sector1Int1 = Integer.parseInt(sector1split[1]);
                        //splits integer and string on the weight
                        String[] weight = sector1split[2].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                        int sector1Int2 = Integer.parseInt(weight[0]);
                        int sector1Int3 = Integer.parseInt(sector1split[3]);
                        Sector1 s1 = new Sector1(sector1split[0], sector1Int1, sector1Int2, sector1Int3);
                        arraySector1.add(s1);
                    }
                    
                    //Splits sector two
                    for (String str : sector2) {
                        sector2split = str.split(";");
                        int sector2Int1 = Integer.parseInt(sector2split[0]);
                        int sector2Int2 = Integer.parseInt(sector2split[1]);
                        Sector2 s2 = new Sector2(sector2Int1, sector2Int2);
                        arraySector2.add(s2);
                    }
                    
                    //Runs commands from textfile and prints new container terminal
                    if ((mySector1.validArray(arraySector1) == true) && (mySector2.validArray(arraySector2) == true)) {
                        for (Sector2 s2 : arraySector2) {
                            terminalCrane.moveContainer(s2.getOrigin(), s2.getTarget(), arraySector1);
                        }
                        terminalCrane.printContainers(arraySector1);
                    }
                }
                else 
                    Terminal.printLine("Error, sector one and/or sector two has/have incorrect strings");
            }
            else
                Terminal.printLine("Error, textfile doesn't contain a first sector, second sector and a boundary");
        }
        else
            Terminal.printLine("Error, File doesn't contain the minimum needed amount of lines");
    }
}
