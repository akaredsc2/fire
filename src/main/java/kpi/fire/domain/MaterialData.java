package kpi.fire.domain;

import kpi.fire.util.ArrayUtils;

public class MaterialData {

    private Material[] materials;

    public MaterialData() {
        materials = new Material[0];
    }

    public MaterialData(Material[] materials) {
        this.materials = materials;
    }

    public double[] getSolidMaterialsLoads() {
        return ArrayUtils.map(materials, Material::getFireLoad);
    }

    public double[] getMinBurnTemperatures() {
        return ArrayUtils.map(materials, Material::getMinBurnTemperature);
    }

    public double[] getAverageBurnSpeeds() {
        return ArrayUtils.map(materials, Material::getAverageBurnSpeed);
    }

    public double[] getAirToBurnAmounts() {
        return ArrayUtils.map(materials, Material::getAirToBurn);
    }
}
