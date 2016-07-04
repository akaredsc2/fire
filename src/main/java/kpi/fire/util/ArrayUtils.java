package kpi.fire.util;

import java.util.function.Function;

public class ArrayUtils {

    public static <T> double[] map(T[] materials, Function<T, Double> function) {
        double[] result = new double[materials.length];

        for (int i = 0; i < materials.length; i++) {
            result[i] = function.apply(materials[i]);
        }

        return result;
    }

}
