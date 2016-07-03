package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

public class FireStats {

    private double fireLoad;
    private FireKind fireKind;

    public FireStats() {
        this.fireLoad = 0.0;
        this.fireKind = null;
    }

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
            double floorArea = data.getFloorArea();
            for (int i = 0; i < spaces.length; i++) {
                result += spaces[i] * sqrt(heights[i]) / floorArea;
            }
        }

        return result;
    }

    private static double computeAirPerFireLoad(double[] materialLoads, double[] airPerMaterial) {
        return dotProduct(materialLoads, airPerMaterial) / sum(materialLoads);
    }

    private static double computeFireLoad(FireInspectionData data, double[] materialBurningTemperature) {
        return dotProduct(data.getSolidMaterialsLoads(), materialBurningTemperature)
                / ((6 * pow(data.getVolume(), 0.667) - sum(data.getApertureSpaces())) * 13.8);
    }

}
