package edu.kit.informatik;

/**
 * Gets a *.txt file over the command line arguments. First the program checks if syntax of
 * the file is correct. Then it calculates the necessary amount of drones the bounty hunter
 * needs to search for the criminal in all planets.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    
    /**
     * Maximal amount of planets that can exist
     */
    private static final int PLANETS = 1000;
    
    /**
     * Calculates the necessary amount of drones the bounty hunter has to use.
     * @param args *.txt file at args[0] with the information about the planets
     * and their portals.
     */
    public static void main(String args[]) {
        String[] file = Terminal.readFile(args[0]);
        SolarSystem solarSystem = new SolarSystem();
        
        //Checks syntax of *txt. file
        if (file[0].split(" ").length == 1 && isNumber(file[0]) && 1 < Integer.parseInt(file[0])
                && Integer.parseInt(file[0]) <= PLANETS) {
            int planetnumber = Integer.parseInt(file[0]);
            
            if (file.length == planetnumber + 1) {
                boolean isCorrect = false;
                for (int i = 1; i < file.length; i++) {
                    if (correctLine(file[i])) {
                        isCorrect = true;
                    }
                    else {
                        isCorrect = false;
                        break;
                    }
                }
                
                //In and out portals
                for (int i = 1; i < file.length; i++) {
                    
                    if (file[i].length() == 1 && edgesOut(file, i - 1) <= 1) {
                        continue;
                    }
                    else if (file[i].length() > 1 && file[i].length() - 1 >= edgesOut(file, i - 1)) {
                        continue;
                    }
                    else {
                        isCorrect = false;
                        break;
                    }
                }
                
                //Syntax of file is correct
                if (isCorrect) {
                    
                    //Add all planets
                    for (int i = 1; i < file.length; i++) {
                        solarSystem.add(file[i], i - 1); 
                    }
                    
                    //Search for possible cycles
                    if (!solarSystem.hasCycles()) {
                        Terminal.printLine(solarSystem.travel());
                    }
                    else
                        Terminal.printError("at least one network in the hyperspace isn't cycle-free");
                }
                else
                    Terminal.printError("file doesn't match required specifications");
            }
            else
                Terminal.printError("number of lines in file is incorrect");
        }
        else 
            Terminal.printError("first line of file is incorrect");
    }
    
    
    /**
     * Checks if a String is an Integer. Returns true if the String can be
     * converted into an Integer.
     * @param string The String to be analyzed.
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
    
    /**
     * Checks if the syntax of an individual line is correct.
     * @param line Line to be analyzed.
     * @return True if the line matches all required requisites.
     */
    private static boolean correctLine(String line) {
        boolean flag = true;
        String[] strArray = line.split(" ");
        
        if (isNumber(strArray[0])) {
            int lineLength = Integer.parseInt(strArray[0]);
            
            if (strArray.length == lineLength + 1) {
                
                //Zero
                if (strArray.length == 1 && Integer.parseInt(strArray[0]) != 0) {
                    flag = false;
                }
                
                for (int i = 1; i < strArray.length; i++) {
                    if (isNumber(strArray[i])) {
                        continue;
                    }
                    else {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }
    
    /**
     * Counts edges between planet, because the degree of entry has to be
     * greater or equal than the degree of exit.
     * @param str Array of Strings with information about the planats and portals.
     * @param index of the planet to analyze.
     * @return Number of portals that go out of the planet.
     */
    private static int edgesOut(String[] str, int index) {
        int counter = 0;
        for (int i = 1; i < str.length; i++) {
            String[] split = str[i].split(" ");
            
            for (int j = 1; j < split.length; j++) {
                if (Integer.parseInt(split[j]) == index) {
                    counter++;
                }
                else {
                    continue;
                }
            }
        }   
        return counter;
    }
    
}