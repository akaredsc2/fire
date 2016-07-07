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

    public double computeMaxVolumeAverageTemperature(double initialVolumeAverageTemperature) {
        double result = 0.0;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = initialVolumeAverageTemperature + 224 * pow(fireStats.getFireLoad(), 0.528);
        } else {
            double fireDuration = data.computeFireDuration();

            if (fireDuration >= 0.15 && fireDuration <= 1.22) {
                double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();
                result = 940 * exp(0.0047 * (fireLoad - 30));
            }
        }

        return result;
    }

    public double computeMaxTemperatureTime() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 32 - 8.1 * pow(fireStats.getFireLoad(), 3.2) * exp(-0.92 * fireStats.getFireLoad());
        } else {
            result = data.computeFireDuration();
        }

        return result;
    }

}
