package kpi.fire.domain;

import java.util.Formatter;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Task5 implements ReportableTask {

    private FireStats fireStats;
    private FireInspectionData data;

    public Task5(FireInspectionData data, FireStats fileStats) {
        this.fireStats = fileStats;
        this.data = data;
    }

    public double computeMaxDensityForWallConstruction() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 3.57 * pow(fireStats.getFireLoad(), 0.75);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = data.computeFireDuration();

            durationFire = data.limitFireDuration(durationFire);

            if (durationFire >= 0.15 && durationFire < 0.8) {
                result = 43 - 75 * durationFire + 50 * pow(durationFire, 2.0);
                return result;
            } else {
                return 15.0;
            }
        }
    }

    public double computeMaxDensityForCoverageConstruction() {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = pow(0.26 * pow(fireStats.getFireLoad(), 0.75) - 3.3 * pow(10.0, -2.0) * pow(fireStats.getFireLoad(), 4.25) * exp(-1.6 * fireStats.getFireLoad()), -1.0);
            return result;
        } else { // fireKing == FireKind.LOAD_REGULATED
            double durationFire = data.computeFireDuration();

            durationFire = data.limitFireDuration(durationFire);

            if (durationFire >= 0.15 && durationFire < 0.8) {
                result = 65 - 138 * durationFire + 97 * pow(durationFire, 2.0);
                return result;
            } else {
                return 17.3;
            }
        }
    }

    public double computeTimeAchievementMaxDensityForWallConstruction() {
        return 26 - 5.1 * pow(fireStats.getFireLoad(), 5.0) * exp(-1.6 * fireStats.getFireLoad());
    }

    public double computeTimeAchievementMaxDensityForCoverageConstruction() {
        return 26 - 7.2 * pow(fireStats.getFireLoad(), 5.0) * exp(-1.6 * fireStats.getFireLoad());
    }

    @Override
    public String reportTask(String description) {
        StringBuilder builder = new StringBuilder();
        builder.append(description)
                .append(":")
                .append(System.lineSeparator())
                .append(fireStats.getFireKind().toUkrString()).append(System.lineSeparator())
                .append("Максимальна усереднина щільність ефективного потоку в конструкції стін: ")
                .append(new Formatter().format("%.2f", computeMaxDensityForWallConstruction()) + " кВт/м2.").append(System.lineSeparator())
                .append("Час досягнення максимальної усередненої щільності ефективного потоку в конструкції стін: ")
                .append(new Formatter().format("%.2f", computeTimeAchievementMaxDensityForWallConstruction()) + " хв.").append(System.lineSeparator())
                .append("Максимальна усереднина щільність ефективного потоку в конструкції покриття: ")
                .append(new Formatter().format("%.2f", computeMaxDensityForCoverageConstruction()) + " кВт/м2.").append(System.lineSeparator())
                .append("Час досягнення максимальної усередненої щільності ефективного потоку в конструкції покриття: ")
                .append(new Formatter().format("%.2f", computeTimeAchievementMaxDensityForCoverageConstruction()) + " хв.").append(System.lineSeparator());
        return builder.toString();
    }

}
