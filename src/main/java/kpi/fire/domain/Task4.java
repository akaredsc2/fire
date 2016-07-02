package kpi.fire.domain;

import static java.lang.Math.*;

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
        double result;

        double firstAuxiliarySum = 0.0;
        double[] materials = data.getSolidMaterialsLoads();
        for (int i = 0; i < materials.length; i++) {
            firstAuxiliarySum += materials[i] * materialBurningTemperature[i];
        }

        double totalApertureSpace = 0.0;
        double[] apertureSpaces = data.getApertureSpaces();
        for (int i = 0; i < apertureSpaces.length; i++) {
            totalApertureSpace += apertureSpaces[i];
        }

        double totalSolidMaterialLoads = 0.0;
        double[] solidMaterialLoads = data.getSolidMaterialsLoads();
        for (int i = 0; i < solidMaterialLoads.length; i++) {
            totalSolidMaterialLoads += solidMaterialLoads[i];
        }

        double secondAuxiliarySum = 0.0;
        for (int i = 0; i < componentAvrSpeedBurn.length; i++) {
            secondAuxiliarySum += componentAvrSpeedBurn[i] * solidMaterialLoads[i];
        }

        result = firstAuxiliarySum * timberAvrSpeedBurn * totalSolidMaterialLoads / (6285 * totalApertureSpace * sqrt(data.getReducedH()) * secondAuxiliarySum);

        return result;
    }
}
