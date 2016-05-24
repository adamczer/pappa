package juav.simulator.nps.random;

import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect3;

import java.util.Random;

/**
 * Created by adamczer on 5/23/16.
 */
public class NpsRandom {
    private static final Random random = new Random();

    public static void double_vect3_add_gaussian_noise(Vect3<Double> vect, Vect3<Double> std_dev)
    {
        vect.setX(vect.getX()+ get_gaussian_noise() * std_dev.getX());
        vect.setY(vect.getY()+ get_gaussian_noise() * std_dev.getY());
        vect.setZ(vect.getZ()+ get_gaussian_noise() * std_dev.getZ());
    }

    public static void float_vect3_add_gaussian_noise(Vect3<Float> vect, Vect3<Float> std_dev)
    {
        vect.setX(vect.getX()+ (float)get_gaussian_noise() * std_dev.getX());
        vect.setY(vect.getY()+ (float)get_gaussian_noise() * std_dev.getY());
        vect.setZ(vect.getZ()+ (float)get_gaussian_noise() * std_dev.getZ());
    }

    public static void float_rates_add_gaussian_noise(Rates<Float> vect, Rates<Float> std_dev)
    {
        vect.setP(vect.getP()+ (float)get_gaussian_noise() * std_dev.getP());
        vect.setQ(vect.getQ()+ (float)get_gaussian_noise() * std_dev.getQ());
        vect.setR(vect.getR()+ (float)get_gaussian_noise() * std_dev.getR());
    }



    public static void double_vect3_get_gaussian_noise(Vect3<Double> vect, Vect3<Double> std_dev)
    {
        vect.setX( get_gaussian_noise() * std_dev.getX());
        vect.setY( get_gaussian_noise() * std_dev.getY());
        vect.setZ( get_gaussian_noise() * std_dev.getZ());
    }

    private static double get_gaussian_noise() {
        return random.nextGaussian();
    }


    public static void double_vect3_update_random_walk(Vect3<Double> rw, Vect3<Double> std_dev, double dt, double thau)
    {
        Vect3<Double> drw = new Vect3<>();
        double_vect3_get_gaussian_noise(drw, std_dev);
        Vect3<Double> tmp = new Vect3<>();
        PprzAlgebra.VECT3_SMUL(tmp, rw, (-1. / thau));
        PprzAlgebra.VECT3_ADD(drw, tmp);
        PprzAlgebra.VECT3_SMUL(drw, drw, dt);
        PprzAlgebra.VECT3_ADD(rw, drw);
    }

}
