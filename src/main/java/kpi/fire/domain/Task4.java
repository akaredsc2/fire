package kpi.fire.domain;

import java.util.Formatter;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Task4 implements ReportableTask {

    private FireStats fireStats;
    private FireInspectionData data;

    public Task4(FireInspectionData data, FireStats fireStats) {
        this.fireStats = fireStats;
        this.data = data;
    }

    public double computeMaxTemperature() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = data.getInitialAverageOverlappingAreaTemperature() + 115 * pow(fireStats.getFireLoad(), 0.68);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = data.computeFireDuration();

            durationFire = data.limitFireDuration(durationFire);

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
        builder.append(description + ":").append(System.lineSeparator())
                .append(fireStats.getFireKind().toUkrString()).append(System.lineSeparator()).append("Максимальна усереднина температура поверхні стін: ")
                .append(new Formatter().format("%.2f", computeMaxTemperature() - 273) + " C.").append(System.lineSeparator())
                .append("Час досягнення максильного значення усередненої температури поверхні стін: ")
                .append(new Formatter().format("%.2f", computeTimeAchievementMaxTemperature()) + " год.").append(System.lineSeparator());
        return builder.toString();
    }

}
