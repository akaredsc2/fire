package kpi.fire.domain;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Task4 implements ReportableTask{

    private FireStats fireStats;
    private FireInspectionData data;
    private double initialAverageOverlappingAreaTemperature;

    public Task4(FireStats fileStats, FireInspectionData data, double initialAverageOverlappingAreaTemperature) {
        this.fireStats = fileStats;
        this.data = data;
        this.initialAverageOverlappingAreaTemperature = initialAverageOverlappingAreaTemperature;
    }

    public double computeMaxTemperature() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = initialAverageOverlappingAreaTemperature + 115 * pow(fireStats.getFireLoad(), 0.68);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = data.computeFireDuration();

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

    public double computeTimeAchievementMaxTemperature() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 35 - 9.3 * pow(fireStats.getFireLoad(), 1.55) * exp(-0.445 * fireStats.getFireLoad());
            return result;
        } else { // fireKind == FireKind.VENTILATION_REGULATED
            result = 1.1 * data.computeFireDuration();
            return result;
        }
    }

    @Override
    public String reportTask(String description) {
        StringBuilder builder = new StringBuilder();
        builder.append(description).append(System.lineSeparator())
                .append(fireStats.getFireKind().toUkrString()).append(System.lineSeparator())
                .append("Максимальна усереднина температура поверхні стін: ")
                .append(computeMaxTemperature()).append(System.lineSeparator())
                .append("Час досягнення максильного значення усередненої температури поверхні стін: ")
                .append(computeTimeAchievementMaxTemperature()).append(System.lineSeparator());
        return builder.toString();
    }
}
