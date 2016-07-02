package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

public class Task4 {

    private static final double TW0 = -1.0;

    private FireStats fireStats;
    private FireInspectionData data;

    public Task4(FireStats fileStats, FireInspectionData data) {
        this.fireStats = fileStats;
        this.data = data;
    }

    public double computeMaxTemperature(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = TW0 + 115 * pow(fireStats.getFireLoad(), 0.68);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

            if (durationFire >= 0.15 && durationFire < 0.8) {
                result = 250 + 1750 * durationFire - 1250 * pow(durationFire, 2.0);
                return result;
            } else if (durationFire >= 0.8 && durationFire <= 1.22) {
                return 850.0;
            } else {
                return -1.0; //what will be here
            }
        }
    }

    public double computeTimeAchievementMaxTemperature(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 35 - 9.3 * pow(fireStats.getFireLoad(), 1.55) * exp(-0.445 * fireStats.getFireLoad());
            return result;
        } else { // fireKind == FireKind.VENTILATION_REGULATED
            result = 1.1 * computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);
            return result;
        }
    }

    public double computeDurationFire(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double firstAuxiliarySum = dotProduct(data.getSolidMaterialsLoads(), materialBurningTemperature);

        double totalApertureSpace = sum(data.getApertureSpaces());

        double totalSolidMaterialLoads = sum(data.getSolidMaterialsLoads());

        double secondAuxiliarySum = dotProduct(componentAvrSpeedBurn, data.getSolidMaterialsLoads());

        return firstAuxiliarySum * timberAvrSpeedBurn * totalSolidMaterialLoads
                / (6285 * totalApertureSpace * sqrt(data.getReducedH()) * secondAuxiliarySum);
    }
}
