package kpi.fire;

import static java.lang.Math.*;

public class Task6 {

    private FireInspectionData data;

    public Task6(FireInspectionData data) {
        this.data = data;
    }

    public double computeMaxDensityOfHeatFlow(double[] materialBurningTemperature, double timberAvrSpeedBurn, double[] componentAvrSpeedBurn) {
        Task4 task4 = new Task4(new FireStats(), data);
        double durationFire = task4.computeDurationFire(materialBurningTemperature, timberAvrSpeedBurn, componentAvrSpeedBurn);

        double totalSolidMaterialLoads = 0.0;
        double[] solidMaterialLoads = data.getSolidMaterialsLoads();
        for (int i = 0; i < solidMaterialLoads.length; i++) {
            totalSolidMaterialLoads += solidMaterialLoads[i];
        }

        double floorArea = data.getVolume() / data.getHeight();

        double fireLoad = totalSolidMaterialLoads / floorArea;

        return 965 - 620.9 * durationFire + 229.2 * pow(durationFire, 2.0) + 10 * (fireLoad - 30);
    }
}
