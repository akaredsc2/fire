package kpi.fire.domain;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static kpi.fire.util.MathUtils.sum;

public class Task3 implements ReportableTask {

    private FireInspectionData data;
    private FireStats fireStats;
    private double initialAverageOverlappingAreaTemperature;

    public Task3(FireInspectionData data, FireStats fireStats, double initialAverageOverlappingAreaTemperature) {
        this.data = data;
        this.fireStats = fireStats;
        this.initialAverageOverlappingAreaTemperature = initialAverageOverlappingAreaTemperature;
    }

    public double computeMaxAverageOverlappingAreaTemperature() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 130 * pow(fireStats.getFireLoad(), 0.64) + this.initialAverageOverlappingAreaTemperature;
        } else {
            double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();
            result = 915 * exp(5 * pow(10, -3) * (fireLoad - 30));
        }

        return result;
    }

    public double computeMaxAverageTemperatureTime() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 40 - 17.3 * pow(fireStats.getFireLoad(), 1.32) * exp(-0.4 * fireStats.getFireLoad());
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
                .append("Максимальна усереднина температура поверхні перекриття: ")
                .append(computeMaxAverageOverlappingAreaTemperature()).append(System.lineSeparator())
                .append("Час досягнення максильного значення усередньої температури поверхні перекриття: ")
                .append(computeMaxAverageTemperatureTime()).append(System.lineSeparator());
        return builder.toString();
    }
}