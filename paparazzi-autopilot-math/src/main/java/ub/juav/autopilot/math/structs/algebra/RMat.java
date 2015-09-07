package ub.juav.autopilot.math.structs.algebra;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class RMat<T extends Number> extends Mat33<T> {
    public void setFlattendElement(int index, T val) {
        int x=-1,y=-1;
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (count==index) {
                    x = i;
                    y = j;
                    break;
                }
                else {
                    count++;
                }
            }
            if (count==index) {
                break;
            }
        }
        setElement(val,x,y);
    }
}
