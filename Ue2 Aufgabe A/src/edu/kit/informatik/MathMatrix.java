package edu.kit.informatik;

/**
 * Implements methods to show, add and multiply matrices and vectors. Overrides
 * toString() method in order to display matrices and vectors.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class MathMatrix {
    private int[][] matrix;
    
    /**
     * Public constructor for a matrix.
     * @param matrix A two-dimensional Array that will be converted into a matrix.
     */
    public MathMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    
    /**
     * Adds two n x n matrices and returns the result.
     * @param addMatrix1 The first matrix for the add-operation.
     * @param addMatrix2 The second matrix for the add-operation.
     * @return Returns the result of the add-operation as a two-dimensional Array.
     */
    public int[][] add(MathMatrix addMatrix1, MathMatrix addMatrix2) {
        int[][] result = new int[addMatrix1.matrix.length][addMatrix1.matrix[0].length];
        
        //Add both numbers with the same index.
        for (int i = 0; i < addMatrix1.matrix.length; i++) {
            for (int j = 0; j < addMatrix2.matrix[0].length; j++) {
                result[i][j] = addMatrix1.matrix[i][j] + addMatrix2.matrix[i][j];
            }
        }
        return result;
    }
    
    /**
     * Multiplies a n x n matrix with a n x 1 vector and returns the result.
     * @param multiply1 The n x n matrix for the multiply-operation.
     * @param multiply2 The n x 1 vector for the multiply-operation.
     * @return Returns the n x 1 vector as a result of the multiplication.
     */
    public int[][] multiply(MathMatrix multiply1, MathMatrix multiply2) {
        int[][] result = new int[multiply1.matrix.length][multiply2.matrix[0].length];
        
        for (int i = 0; i < multiply1.matrix.length; i++) {
            for (int j = 0; j < multiply2.matrix[0].length; j++) {
                result[i][j] = 0;
                
                for (int k = 0; k < multiply1.matrix[0].length; k++) {
                    result[i][j] += multiply1.matrix[i][k] * multiply2.matrix[k][j];
                }
            }
        }
        return result;
    }
    
    /**
     * Overrides the toString() method in order to display matrices and vectors.
     */
    @Override
    public String toString() {
        String result = "";
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j + 1 < matrix[0].length) {
                    //result.append(matrix[i][j] + " ");
                    result = result + matrix[i][j] + " ";
                }
                else if (i + 1 < matrix.length) {
                    //result.append(matrix[i][j]);
                    result = result + matrix[i][j] + "\n";
                    //result.append("\n");
                }
                else {
                    //result.append(matrix[i][j]);
                    result = result + matrix[i][j];
                }
            }
        }
        return result;
    }
}
