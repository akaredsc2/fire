package kpi.fire;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Task2Test {

    private FireInspectionData data;
    private FireStats stats;
    private Task2 task2;

    @Before
    public void setUp() throws Exception {
        data = FireInspectionData.create()
                .setVolume(14040.0)
                .setApertureSpaces(new double[]{167.0})
                .setApertureHeights(new double[]{2.89})
                .setHeight(6.0)
                .setSolidMaterialsLoads(new double[]{46800.0});
        stats = FireStats.computeFireStats(data);
        task2 = new Task2(data, stats);
    }

    @Test
    public void compute() throws Exception {
        assertEquals(0.4, task2.computeMaxTemperatureTime(new double[]{13.8}, 2.4, new double[]{2.4}), 0.1);
        assertEquals(897, task2.computeMaxVolumeAverageTemperature(293.0, new double[]{13.8}, 2.4, new double[]{2.4}), 1.0);
    }

}