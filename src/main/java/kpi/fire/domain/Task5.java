package kpi.fire.domain;

import static java.lang.Math.*;

public class Task5 {

    private FireStats fireStats;
    private FireInspectionData data;

    public Task5(FireStats fileStats, FireInspectionData data) {
        this.fireStats = fileStats;
        this.data = data;
    }

    public double computeMaxDensityForWallConstruction(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 3.57 * pow(fireStats.getFireLoad(), 0.75);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            Task4 task4 = new Task4(fireStats, data);
            double durationFire = task4.computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

            if (durationFire > 0.15 && durationFire < 0.8) {
                result = 43 - 75 * durationFire + 50 * pow(durationFire, 2.0);
                return result;
            } else if (durationFire >= 0.8 && durationFire <= 1.22) {
                return 15.0;
            } else {
                return -1.0; //what will be here
            }
        }
    }

    public double computeMaxDensityForCoverageConstruction(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = pow(0.26 * pow(fireStats.getFireLoad(), 0.75) - 3.3 * pow(10.0, -2.0) * pow(fireStats.getFireLoad(), 4.25) * exp(-1.6 * fireStats.getFireLoad()), -1.0);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            Task4 task4 = new Task4(fireStats, data);
            double durationFire = task4.computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

            if (durationFire > 0.15 && durationFire < 0.8) {
                result = 65 - 138 * durationFire + 97 * pow(durationFire, 2.0);
                return result;
            } else if (durationFire >= 0.8 && durationFire <= 1.22) {
                return 17.3;
            } else {
                return -1.0; //what will be here
            }
        }
    }

    public double computeTimeAchievementMaxDensityForWallConstruction() {
        return 26 - 5.1 * pow(fireStats.getFireLoad(), 5.0) * exp(-1.6 * fireStats.getFireLoad());
    }

    public double computeTimeAchievementMaxDensityForCoverageConstruction() {
        return 26 - 7.2 * pow(fireStats.getFireLoad(), 5.0) * exp(-1.6 * fireStats.getFireLoad());
    }
}