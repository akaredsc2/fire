package kpi.fire;

import static java.lang.Math.*;

public class FireStats {

    private double fireLoad;
    private FireKind fireKind;

    private FireStats(double fireLoad, FireKind fireKind) {
        this.fireLoad = fireLoad;
        this.fireKind = fireKind;
    }

    public double getFireLoad() {
        return fireLoad;
    }

    public FireKind getFireKind() {
        return fireKind;
    }

    public static FireStats computeFireStats(FireInspectionData data) {
        double placementAperture = computeRoomAperture(data);
        // FIXME: 01-Jul-16 replace hardcoded array with proper values
        double airPerFireLoad = computeAirPerFireLoad(data.getSolidMaterialsLoads(), new double[]{4.2});
        double criticalFireLoad = 4500 * pow(placementAperture, 3) / (1 + 500 * pow(placementAperture, 3))
                + pow(data.getVolume(), 1.0 / 3.0) / (6 * airPerFireLoad);
        // FIXME: 01-Jul-16 replace hardcoded array with proper values
        double fireLoad = computeFireLoad(data, new double[]{13.8});

        if (fireLoad < criticalFireLoad) {
            return new FireStats(fireLoad, FireKind.LOAD_REGULATED);
        } else {
            return new FireStats(fireLoad, FireKind.VENTILATION_REGULATED);
        }
    }

    private static double computeRoomAperture(FireInspectionData data) {
        double result = 0.0;

        double volume = data.getVolume();
        double[] spaces = data.getApertureSpaces();
        double[] heights = data.getApertureHeights();

        if (volume <= 10.0) {
            for (int i = 0; i < spaces.length; i++) {
                result += spaces[i] * sqrt(heights[i]) / pow(volume, 2.0 / 3.0);
            }
        } else {
            double floorArea = volume / data.getHeight();
            for (int i = 0; i < spaces.length; i++) {
                result += spaces[i] * sqrt(heights[i]) / floorArea;
            }
        }

        return result;
    }

    private static double computeAirPerFireLoad(double[] materialLoads, double[] airPerMaterial) {
        double result = 0.0;

        for (int i = 0; i < materialLoads.length; i++) {
            result += materialLoads[i] * airPerMaterial[i];
        }
        double totalMaterialLoad = 0.0;
        for (int i = 0; i < materialLoads.length; i++) {
            totalMaterialLoad += materialLoads[i];
        }

        return result / totalMaterialLoad;
    }

    private static double computeFireLoad(FireInspectionData data, double[] materialBurningTemperature) {
        double result = 0.0;

        double[] materials = data.getSolidMaterialsLoads();
        for (int i = 0; i < data.getSolidMaterialsLoads().length; i++) {
            result += materials[i] * materialBurningTemperature[i];
        }

        double totalApertureSpace = 0.0;
        double[] apertureSpaces = data.getApertureSpaces();
        for (int i = 0; i < apertureSpaces.length; i++) {
            totalApertureSpace += apertureSpaces[i];

        }
        result = result / ((6 * pow(data.getVolume(), 2.0 / 3.0) - totalApertureSpace) * min(materialBurningTemperature));

        return result;
    }

    private static double min(double[] array) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
}
