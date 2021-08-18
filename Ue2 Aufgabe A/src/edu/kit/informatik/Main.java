package edu.kit.informatik;

/**
 * Implements the main method for the matrix-operations program and two other methods
 * to create the arrays for the matrices and vectors.
 * @author Emilio Rivera
 * @version 1.0
 * Tutor: ich habe buildMatrix und buildVector als private definiert, weil diese Methoden
 * nur von der main Methode verwendet werden und es somit nicht nötig ist von außen Zugriff
 * zu haben. Ist das so richtig oder hätte ich sie lieber doch al public definieren sollen?
 *
 */
public class Main {
    private static int[][] aMatrix;
    
    /**
     * Main Method for the class. Receives an input from the Command Line Arguments and
     * shows, adds or multiplies the matrix/matrices.
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        MathMatrix myMatrix1;
        MathMatrix myMatrix2;
        MathMatrix myMatrix3 = null;
        switch (args[0]) {
        case "show":
            //Initialize matrix to be displayed.
            
            if (!isVector(args[1])) {
                aMatrix = biuldMatrix(args[1]);
                myMatrix1 = new MathMatrix(aMatrix);
            }
            else {
                aMatrix = buildVector(args[1]);
                myMatrix1 = new MathMatrix(aMatrix);
            }
            
            Terminal.printLine(myMatrix1.toString());
            break;
            
        case "add":
            //Initialize matrices that will be added.
            aMatrix = biuldMatrix(args[1]);
            myMatrix1 = new MathMatrix(aMatrix);   
            aMatrix = biuldMatrix(args[2]);
            myMatrix2 = new MathMatrix(aMatrix);
            
            myMatrix3 = new MathMatrix(myMatrix1.add(myMatrix1, myMatrix2));    
            Terminal.printLine(myMatrix3.toString());
            break;
            
        case "multiply":
            //Initialize matrix and vector that will be multiplied.
            aMatrix = biuldMatrix(args[1]);
            myMatrix1 = new MathMatrix(aMatrix);
            aMatrix = buildVector(args[2]);
            myMatrix2 = new MathMatrix(aMatrix);
            
            myMatrix3 = new MathMatrix(myMatrix1.multiply(myMatrix1, myMatrix2));
            Terminal.printLine(myMatrix3.toString());
            break;
            
        default:
            break;
        }
    }
    
    /**
     * Builds a matrix with the information from a parameter.
     * @param something Contains the necessary information to build a matrix.
     * @return A two-dimensional Array with the matrix information.
     */
    private static int[][] biuldMatrix(String something) {
        int counter = 0;
        String[] arrayRows = something.split(";");
        String[] rowLength = arrayRows[0].split(",");
        
        String numbers1 = something.replaceAll(",", " ");
        String numbers2 = numbers1.replaceAll(";", " ");
        String[] numbersArray = numbers2.split(" ");
        
        //Define matrix size.
        aMatrix = new int[rowLength.length][rowLength.length];
        
        for (int i = 0; i < rowLength.length; i++) {      
            for (int j = 0; j < rowLength.length; j++) {
                
                aMatrix[i][j] = Integer.parseInt(numbersArray[counter]);
                counter++;
            }
        }
        return aMatrix;
    }
    
    /**
     * Builds a vector with the information from a parameter.
     * @param something Contains the necessary information to build a vector.
     * @return An Array with the vector information.
     */
    private static int[][] buildVector(String something) {
        int counter = 0;
        String[] arrayRows = something.split(";");
        
        String numbers1 = something.replaceAll(",", " ");
        String numbers2 = numbers1.replaceAll(";", " ");
        String[] numbersArray = numbers2.split(" ");
        
        //Define vector size.
        aMatrix = new int[arrayRows.length][1];
        
        for (int i = 0; i < arrayRows.length; i++) {
            for (int j = 0; j < 1; j++) {
                
                aMatrix [i][j] = Integer.parseInt(numbersArray[counter]);
                counter++;
            }
        }      
        return aMatrix;
    }
    
    /**
     * Checks if the input corresponds to a matrix or a vector.
     * @param something Contains the information about the matrix or vector.
     * @return True if it is a vector, false if it is a matrix.
     */
    private static boolean isVector(String something) {
        boolean vector = false;
        String[] arrayRows = something.split(";");
        String[] rowLength = arrayRows[0].split(",");
        if (rowLength.length > 1) {
            vector = false;
        }
        else {
            vector = true;
        }
        return vector;
    }
}