package kpi.fire.util;

public class MathUtils {

    public static double sum(double[] array) {
        double result = 0.0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result;
    }

    public static double dotProduct(double[] first, double[] second) {
        if (first.length != second.length) {
            throw new IllegalArgumentException("Different array lengths");
        }
        double result = 0.0;
        for (int i = 0; i < first.length; i++) {
            result += first[i] * second[i];
        }
        return result;
    }

}