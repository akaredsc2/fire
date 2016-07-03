package kpi.fire.domain;

import static java.lang.Math.sqrt;
import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

public class FireInspectionData {

    private double volume;
    private double[] apertureSpaces;
    private double[] apertureHeights;
    private MaterialData materialData;
    private double height;

    private FireInspectionData() {
        this.volume = 0;
        this.apertureSpaces = new double[0];
        this.apertureHeights = new double[0];
        this.materialData = new MaterialData();
        this.height = 0;
    }

    public double getVolume() {
        return volume;
    }

    public double[] getApertureSpaces() {
        return apertureSpaces;
    }

    public double[] getApertureHeights() {
        return apertureHeights;
    }

    public MaterialData getMaterialData() {
        return materialData;
    }

    public double getReducedH() {
        return dotProduct(apertureSpaces, apertureHeights) / sum(apertureSpaces);
    }

    public double getHeight() {
        return height;
    }

    public double getFloorArea() {
        return volume / height;
    }

    public FireInspectionData setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public FireInspectionData setApertureSpaces(double[] apertureSpaces) {
        this.apertureSpaces = apertureSpaces;
        return this;
    }

    public FireInspectionData setApertureHeights(double[] apertureHeights) {
        this.apertureHeights = apertureHeights;
        return this;
    }

    public FireInspectionData setMaterialData(MaterialData materialData) {
        this.materialData = materialData;
        return this;
    }

    public FireInspectionData setHeight(double height) {
        this.height = height;
        return this;
    }

    public static FireInspectionData create() {
        return new FireInspectionData();
    }

    public double computeFireDuration(double timberAvrSpeedBurn) {
        double firstAuxiliarySum = dotProduct(materialData.getSolidMaterialsLoads(), materialData.getMinBurnTemperatures());

        double totalApertureSpace = sum(getApertureSpaces());

        double totalSolidMaterialLoads = sum(materialData.getSolidMaterialsLoads());

        double secondAuxiliarySum = dotProduct(materialData.getAverageBurnSpeeds(), materialData.getSolidMaterialsLoads());

        return firstAuxiliarySum * timberAvrSpeedBurn * totalSolidMaterialLoads
                / (6285 * totalApertureSpace * sqrt(getReducedH()) * secondAuxiliarySum);
    }
}
