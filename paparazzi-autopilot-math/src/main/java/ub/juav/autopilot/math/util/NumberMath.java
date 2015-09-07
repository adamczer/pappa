package ub.juav.autopilot.math.util;

import ub.juav.autopilot.math.functions.algebra.PprzAlgebraInt;

/**
 * Created by adamczer on 7/15/15.
 */
public class NumberMath {
    public static Number add(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 + (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 + (Integer) num2;
            } 
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static Number sub(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 - (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 - (Integer) num2;
            }
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static Number mul(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 * (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 * (Integer) num2;
            }
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static Number div(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 / (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 / (Integer) num2;
            }
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static boolean lessThan(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 < (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 < (Integer) num2;
            }
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static boolean greaterThan(Number num1, Number num2) {
        if(num1 instanceof Double) {
            if(num2 instanceof Double)
                return (Double) num1 > (Double) num2;
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else if(num1 instanceof Integer) {
            if(num2 instanceof Integer){
                return (Integer) num1 > (Integer) num2;
            }
            else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
        } else throw new IllegalArgumentException("num1 = "+num1.getClass()+" num2 = "+num2.getClass());
    }
    public static Number abs(Number num) {
        if(num instanceof Double) {
            return Math.abs((Double)num);
        } else if(num instanceof Integer) {
            return Math.abs((Integer)num);
        } else throw new IllegalArgumentException("num1 = "+num.getClass());
    }
    public static Number sq(Number num) {
        return mul(num,num);
    }
    public static Number sum(Number ... numbers) {
        Number ret = numbers[0];
        for(int i = 1;i<numbers.length; i++) {
            ret = add(ret,numbers[i]);
        }
        return ret;
    }
    public static Number negate(Number num) {
        if(num instanceof Double) {
            return mul(num, - 1.0);
        } else if(num instanceof Integer) {
            return mul(num, - 1);
        } else throw new IllegalArgumentException("num1 = "+num.getClass());
    }

    public static Number sqrt(Number number) {
        if(number instanceof Integer) {
            return PprzAlgebraInt.int32_sqrt(number.intValue());
        } else if (number instanceof Float) {
            return (float)Math.sqrt(number.floatValue());
        } else return Math.sqrt(number.doubleValue());
    }
}
