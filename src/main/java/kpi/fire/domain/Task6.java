package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.sum;

public class Task6 {

    private FireInspectionData data;

    public Task6(FireInspectionData data) {
        this.data = data;
    }

    public double computeMaxDensityOfHeatFlow(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        Task4 task4 = new Task4(new FireStats(), data);
        double durationFire = task4.computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

        double fireLoad = sum(data.getSolidMaterialsLoads()) / data.getFloorArea();

        return 965 - 620.9 * durationFire + 229.2 * pow(durationFire, 2.0) + 10 * (fireLoad - 30);
    }
}
