package kpi.fire.domain;

import java.util.Formatter;

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
            double densityOfHeatFlow = computeMaxDensityOfHeatFlow();
            builder.append("Максимальна щільність теплового потоку з продуктами горіння, які йдуть через пройоми: ")
                    .append(new Formatter().format("%.2f", densityOfHeatFlow) + " кВт/м2.");
            if (densityOfHeatFlow < 42.0 ) {
                builder.append("Загалом переносимо, можливі почервонніння шкіри");
            } else if (densityOfHeatFlow >= 42.0 && densityOfHeatFlow < 84.0) {
                builder.append("Переносимо, висока ймовірність появи пухирів на тілі");
            } else {
                builder.append("Не переносимо, при ураженні більше ніж 50% шкіри настає летальний випадок");
            }
        } else {
            builder.append("У ГОСТ Р 12.3.047-98 відсутня інформація для даної ситуації");
        }
            builder.append(System.lineSeparator());
        return builder.toString();
    }

}
