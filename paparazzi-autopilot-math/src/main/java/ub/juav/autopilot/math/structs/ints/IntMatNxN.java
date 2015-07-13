package ub.juav.autopilot.math.structs.ints;

/**
 * Created by adamczer on 7/12/15.
 */
public class IntMatNxN {
    private int[][] matrix;
    public IntMatNxN(int x, int y) {
        matrix = new int[x][y];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setElement(int i, int row, int col) {
        matrix[row][col] = i;
    }

    public int getElement(int row,int column) {
        return matrix[row][column];
    }
}
