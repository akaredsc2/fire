package kpi.fire.domain;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static kpi.fire.util.MathUtils.sum;

public class Task2 implements ReportableTask {

    private FireInspectionData data;
    private FireStats fireStats;
    private double initialVolumeAverageTemperature;

    public Task2(FireInspectionData data, FireStats fireStats, double initialVolumeAverageTemperature) {
        this.data = data;
        this.fireStats = fireStats;
        this.initialVolumeAverageTemperature = initialVolumeAverageTemperature;
    }

    public double computeMaxVolumeAverageTemperature() {
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

    @Override
    public String reportTask(String description) {
        StringBuilder builder = new StringBuilder();
        builder.append(description).append(System.lineSeparator())
                .append(fireStats.getFireKind()).append(System.lineSeparator())
                .append("Максимальна середньооб'ємна температура: ")
                .append(computeMaxVolumeAverageTemperature()).append(System.lineSeparator())
                .append("Час досягнення максимального значення середньооб'ємної температури: ")
                .append(computeMaxTemperatureTime()).append(System.lineSeparator());
        return builder.toString();
    }
}
