package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.sum;

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
            double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();
            // FIXME: 02-Jul-16 check K.12 and K.7 formulas' exponents
            result = 915 * exp(5 * pow(10, -3 * (fireLoad - 30)));
        }

        return result;
    }

    public double computeMaxAverageTemperatureTime(double timberAvrSpeedBurn) {
        double result;

        if (fireStats.getFireKind() == FireKind.LOAD_REGULATED) {
            result = 40 - 17.3 * pow(fireStats.getFireLoad(), 1.32) * exp(-0.4 * fireStats.getFireLoad());
        } else {
            result = data.computeFireDuration(timberAvrSpeedBurn);
        }

        return result;
    }

    // TODO: 02-Jul-16 delta temperature (K.14 formula)

}