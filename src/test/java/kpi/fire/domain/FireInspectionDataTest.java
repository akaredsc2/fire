package kpi.fire.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FireInspectionDataTest {

    private FireInspectionData data;

    @Before
    public void setUp() throws Exception {
        data = FireInspectionData.create();
    }

    @Test
    public void testCreate() throws Exception {
        assertEquals(0.0, data.getHeight(), 0.0);
        assertEquals(0.0, data.getVolume(), 0.0);

        data.setHeight(1.0)
                .setVolume(2.0)
                .setApertureHeights(new double[]{3.0, 4.0});

        assertEquals(1.0, data.getHeight(), 0.0);
        assertEquals(2.0, data.getVolume(), 0.0);
        assertArrayEquals(new double[] {3.0, 4.0}, data.getApertureHeights(), 0.0);
    }

}