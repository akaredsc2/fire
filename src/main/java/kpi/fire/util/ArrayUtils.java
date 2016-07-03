package kpi.fire.util;

import kpi.fire.domain.Material;

import java.util.function.Function;

public class ArrayUtils {

    public static double[] map(Material[] materials, Function<Material, Double> function) {
        double[] result = new double[materials.length];

        for (int i = 0; i < materials.length; i++) {
            result[i] = function.apply(materials[i]);
        }

        return result;
    }

    public static String[] mapToNames(Material[] materials) {
        String[] result = new String[materials.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = materials[i].getName();
        }

        return result;
    }

}
