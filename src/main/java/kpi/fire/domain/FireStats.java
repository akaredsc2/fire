package kpi.fire.domain;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

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
        double airPerFireLoad = computeAirPerFireLoad(data.getMaterialData());
        double criticalFireLoad = 4500 * pow(placementAperture, 3) / (1 + 500 * pow(placementAperture, 3))
                + pow(data.getVolume(), 1.0 / 3.0) / (6 * airPerFireLoad);
        double fireLoad = computeFireLoad(data);

        if (fireLoad < criticalFireLoad) {
            return new FireStats(fireLoad, FireKind.LOAD_REGULATED);
        } else {
            return new FireStats(fireLoad, FireKind.VENTILATION_REGULATED);
        }
    }

    private static double computeRoomAperture(FireInspectionData data) {
        double result = 0.0;

        double volume = data.getVolume();
        double[] spaces = data.getApertureData().getApertureSpaces();
        double[] heights = data.getApertureData().getApertureHeights();

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

    private static double computeAirPerFireLoad(MaterialData data) {
        return dotProduct(data.getSolidMaterialsLoads(), data.getAirToBurnAmounts()) / sum(data.getSolidMaterialsLoads());
    }

    private static double computeFireLoad(FireInspectionData data) {
        return dotProduct(data.getMaterialData().getSolidMaterialsLoads(), data.getMaterialData().getMinBurnTemperatures())
                / ((6 * pow(data.getVolume(), 0.667) - sum(data.getApertureData().getApertureSpaces())) * data.getLowestWoodBurnHeat());
    }

}
