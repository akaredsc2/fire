package kpi.fire;

import static java.lang.Math.*;

public class Task2 {

    private FireInspectionData data;
    private FireStats fireStats;

    public double computeMaxVolumeAverageTemperature(double initialVolumeAverageTemperature,
                                                     double[] materialBurningTemperature, double timberAvrSpeedBurn,
                                                     double[] componentAvrSpeedBurn) {
        double result = 0.0;
        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = initialVolumeAverageTemperature + 224 * pow(fireStats.getFireLoad(), 0.528);
        } else {
            double fireDuration = new Task4(fireStats, data)
                    .computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

            if (fireDuration >= 0.15 && fireDuration <= 1.22) {
                // FIXME: 02-Jul-16 extract getters for total loads and floor area
                double totalSolidMaterialLoads = 0.0;
                double[] solidMaterialLoads = data.getSolidMaterialsLoads();
                for (int i = 0; i < solidMaterialLoads.length; i++) {
                    totalSolidMaterialLoads += solidMaterialLoads[i];
                }
                double floorArea = data.getVolume() / data.getHeight();
                double fireLoad = totalSolidMaterialLoads / floorArea;

                result = 940 * exp(0.0047 * (fireLoad - 30));
            }
        }
        return result;
    }

    public double computeMaxVolumeAverageTemperatureTime(double[] materialBurningTemperature, double timberAvrSpeedBurn,
                                                         double[] componentAvrSpeedBurn) {
        double result = 0.0;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 32 - 8.1 * pow(fireStats.getFireLoad(), 3.2) * exp(-0.92 * fireStats.getFireLoad());
        } else {
            // FIXME: 02-Jul-16 extract field
            result = new Task4(fireStats, data).computeDurationFire(materialBurningTemperature,
                    timberAvrSpeedBurn, componentAvrSpeedBurn);
        }

        return result;
    }

    // TODO: 02-Jul-16 delta temperature (K.10 formula)

}
