package kpi.fire;

import static java.lang.Math.*;

public class Task3 {

    private FireInspectionData data;
    private FireStats fireStats;

    public Task3(FireInspectionData data, FireStats fireStats) {
        this.data = data;
        this.fireStats = fireStats;
    }

    public double computeMaxAverageOverlappingAreaTemperature(double initialAverageOverlappingAreaTemperature) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 130 * pow(fireStats.getFireLoad(), 0.64) + initialAverageOverlappingAreaTemperature;
        } else {
            // FIXME: 02-Jul-16 extract getters for total loads and floor area
            double totalSolidMaterialLoads = 0.0;
            double[] solidMaterialLoads = data.getSolidMaterialsLoads();
            for (int i = 0; i < solidMaterialLoads.length; i++) {
                totalSolidMaterialLoads += solidMaterialLoads[i];
            }
            double floorArea = data.getVolume() / data.getHeight();
            double fireLoad = totalSolidMaterialLoads / floorArea;
            // FIXME: 02-Jul-16 check K.12 and K.7 formulas' exponents
            result = 915 * exp(5 * pow(10, -3 * (fireLoad - 30)));
        }

        return result;
    }

    public double computeMaxAverageTemperatureTime(double[] materialBurningTemperature,
                                                   double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 40 - 17.3 * pow(fireStats.getFireLoad(), 1.32) * exp(-0.4 * fireStats.getFireLoad());
        } else {
            result = new Task4(fireStats, data)
                    .computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);
        }

        return result;
    }

    // TODO: 02-Jul-16 delta temperature (K.14 formula)

}