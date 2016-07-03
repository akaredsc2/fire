package kpi.fire.util;

import kpi.fire.domain.Material;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayUtilsTest {

    private Material[] materials;

    @Before
    public void setUp() throws Exception {
        materials = new Material[2];
        materials[0] = new Material("cocaine", 1.0, 2.0, 3.0, 4.0);
        materials[1] = new Material("heroin", 5.0, 6.0, 7.0, 8.0);
    }

    @Test
    public void extract() throws Exception {
        double[] expectedFireLoads = new double[]{1.0, 5.0};
        assertArrayEquals(expectedFireLoads, ArrayUtils.map(materials, Material::getFireLoad), 0.01);

        double[] expectedAirToBurn = new double[]{2.0, 6.0};
        assertArrayEquals(expectedAirToBurn, ArrayUtils.map(materials, Material::getAirToBurn), 0.01);

        double[] expectedBurnTemperature = new double[]{3.0, 7.0};
        assertArrayEquals(expectedBurnTemperature,
                ArrayUtils.map(materials, Material::getMinBurnTemperature), 0.01);

        double[] expectedAverageBurnSpeed = new double[]{4.0, 8.0};
        assertArrayEquals(expectedAverageBurnSpeed,
                ArrayUtils.map(materials, Material::getAverageBurnSpeed), 0.01);

        String[] expectedNames = new String[] {"cocaine", "heroin"};
        assertArrayEquals(expectedNames, ArrayUtils.mapToNames(materials));
    }

}