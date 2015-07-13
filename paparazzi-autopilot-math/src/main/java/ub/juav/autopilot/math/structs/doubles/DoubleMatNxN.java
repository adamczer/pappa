package ub.juav.autopilot.math.structs.doubles;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleMatNxN {
    private double[][] matrix;
    public DoubleMatNxN(int x, int y) {
        matrix = new double[x][y];
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public void setElement(double d, int row, int col) {
        matrix[row][col] = d;
    }

    public double getElement(int row,int column) {
        return matrix[row][column];
    }
}
