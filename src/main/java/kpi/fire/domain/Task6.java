package kpi.fire.domain;

import static java.lang.Math.pow;
import static kpi.fire.util.MathUtils.sum;

public class Task6 implements ReportableTask{

    private FireInspectionData data;

    public Task6(FireInspectionData data) {
        this.data = data;
    }

    public double computeMaxDensityOfHeatFlow() {
        double durationFire = data.computeFireDuration();

        double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();

        return 965 - 620.9 * durationFire + 229.2 * pow(durationFire, 2.0) + 10 * (fireLoad - 30);
    }

    // TODO: 07-Jul-16 if not ventilation
    @Override
    public String reportTask(String description) {
        StringBuilder builder = new StringBuilder();
        builder.append(description).append(System.lineSeparator())
                .append(FireStats.computeFireStats(data).getFireKind()).append(System.lineSeparator())
                .append("Максимальна щільність теплового потоку з продуктами горіння, які йдуть через пройоми: ")
                .append(computeMaxDensityOfHeatFlow()).append(System.lineSeparator());
        return builder.toString();
    }
}
