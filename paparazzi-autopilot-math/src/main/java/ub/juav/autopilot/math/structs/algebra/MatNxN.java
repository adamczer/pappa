package ub.juav.autopilot.math.structs.algebra;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class MatNxN<T extends Number> {
    private T[][] matrix;

    public T[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public void setElement(T d, int row, int col) {
        matrix[row][col] = d;
    }

    public T getElement(int row,int column) {
        return matrix[row][column];
    }

    public abstract void zero();
}
