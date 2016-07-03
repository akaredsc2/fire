package kpi.fire.domain;

import kpi.fire.util.MaterialUtils;

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

    public double computeMaxTemperature(double timberAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = TW0 + 115 * pow(fireStats.getFireLoad(), 0.68);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = computeDurationFire(data.getMaterials(), timberAvrSpeedBurn);

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

    public double computeTimeAchievementMaxTemperature(double timberAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 35 - 9.3 * pow(fireStats.getFireLoad(), 1.55) * exp(-0.445 * fireStats.getFireLoad());
            return result;
        } else { // fireKind == FireKind.VENTILATION_REGULATED
            result = 1.1 * computeDurationFire(data.getMaterials(), timberAvrSpeedBurn);
            return result;
        }
    }

    public double computeDurationFire(Material[] materials, double timberAvrSpeedBurn) {
        double firstAuxiliarySum = dotProduct(data.getSolidMaterialsLoads(), MaterialUtils.extract(materials, Material::getMinBurnTemperature));

        double totalApertureSpace = sum(data.getApertureSpaces());

        double totalSolidMaterialLoads = sum(data.getSolidMaterialsLoads());

        double secondAuxiliarySum = dotProduct(MaterialUtils.extract(materials, Material::getAverageBurnSpeed), data.getSolidMaterialsLoads());

        return firstAuxiliarySum * timberAvrSpeedBurn * totalSolidMaterialLoads
                / (6285 * totalApertureSpace * sqrt(data.getReducedH()) * secondAuxiliarySum);
    }

}
