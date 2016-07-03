package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.sum;

public class Task2 {

    private FireInspectionData data;
    private FireStats fireStats;

    public Task2(FireInspectionData data, FireStats fireStats) {
        this.data = data;
        this.fireStats = fireStats;
    }

    public double computeMaxVolumeAverageTemperature(double initialVolumeAverageTemperature, double timberAvrSpeedBurn) {
        double result = 0.0;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = initialVolumeAverageTemperature + 224 * pow(fireStats.getFireLoad(), 0.528);
        } else {
            double fireDuration = new Task4(fireStats, data)
                    .computeDurationFire(data.getMaterials(), timberAvrSpeedBurn);

            if (fireDuration >= 0.15 && fireDuration <= 1.22) {
                double fireLoad = sum(data.getSolidMaterialsLoads()) / data.getFloorArea();
                result = 940 * exp(0.0047 * (fireLoad - 30));
            }
            // FIXME: 02-Jul-16 if not in 0.15 <= t <= 1.22
        }

        return result;
    }

    public double computeMaxTemperatureTime(double timberAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 32 - 8.1 * pow(fireStats.getFireLoad(), 3.2) * exp(-0.92 * fireStats.getFireLoad());
        } else {
            result = new Task4(fireStats, data).computeDurationFire(data.getMaterials(), timberAvrSpeedBurn);
        }

        return result;
    }

    // TODO: 02-Jul-16 delta temperature (K.10 formula)

}
