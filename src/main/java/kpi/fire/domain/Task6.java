package kpi.fire.domain;

import static java.lang.Math.*;
import static kpi.fire.util.MathUtils.sum;

public class Task6 {

    private FireInspectionData data;

    public Task6(FireInspectionData data) {
        this.data = data;
    }

    public double computeMaxDensityOfHeatFlow(double timberAvrSpeedBurn) {
        double durationFire = data.computeFireDuration(timberAvrSpeedBurn);

        double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();

        return 965 - 620.9 * durationFire + 229.2 * pow(durationFire, 2.0) + 10 * (fireLoad - 30);
    }
}
