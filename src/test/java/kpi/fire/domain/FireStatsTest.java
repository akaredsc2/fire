package kpi.fire.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FireStatsTest {

    private FireInspectionData data;

    @Before
    public void setUp() throws Exception {
        data = FireInspectionData.create()
                .setVolume(14040.0)
                .setApertureSpaces(new double[]{167.0})
                .setApertureHeights(new double[]{2.89})
                .setHeight(6.0)
                .setSolidMaterialsLoads(new double[]{46800.0});
    }

    @Test
    public void computeFireStats() throws Exception {
        assertEquals(FireKind.VENTILATION_REGULATED, FireStats.computeFireStats(data).getFireKind());
    }

}