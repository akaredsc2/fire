package kpi.fire.domain;

import static java.lang.Math.pow;
import static kpi.fire.util.MathUtils.sum;

public class Task6 implements ReportableTask {

    private FireInspectionData data;

    public Task6(FireInspectionData data) {
        this.data = data;
    }

    public double computeMaxDensityOfHeatFlow() {
        double durationFire = data.computeFireDuration();

        double fireLoad = sum(data.getMaterialData().getSolidMaterialsLoads()) / data.getFloorArea();

        return 965 - 620.9 * durationFire + 229.2 * pow(durationFire, 2.0) + 10 * (fireLoad - 30);
    }

    @Override
    public String reportTask(String description) {
        StringBuilder builder = new StringBuilder();

        FireKind fireKind = FireStats.computeFireStats(data).getFireKind();
        builder.append(description).append(System.lineSeparator())
                .append(FireStats.computeFireStats(data).getFireKind().toUkrString()).append(System.lineSeparator());
        if (fireKind == FireKind.VENTILATION_REGULATED) {
            builder.append("Максимальна щільність теплового потоку з продуктами горіння, які йдуть через пройоми: ")
                    .append(computeMaxDensityOfHeatFlow()).append(System.lineSeparator());
        } else {
            builder.append("У ГОСТ Р 12.3.047-98 відсутня інформація для даної ситуації");
        }

        return builder.toString();
    }
}
