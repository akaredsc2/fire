package kpi.fire.domain;

import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

public class FireInspectionData {

    private double volume;
    private double[] apertureSpaces;
    private double[] apertureHeights;
    private double[] solidMaterialsLoads;
    private double height;

    private FireInspectionData() {
        this.volume = 0;
        this.apertureSpaces = new double[0];
        this.apertureHeights = new double[0];
        this.solidMaterialsLoads = new double[0];
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

    public double[] getSolidMaterialsLoads() {
        return solidMaterialsLoads;
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

    public FireInspectionData setSolidMaterialsLoads(double[] solidMaterialsLoads) {
        this.solidMaterialsLoads = solidMaterialsLoads;
        return this;
    }

    public FireInspectionData setHeight(double height) {
        this.height = height;
        return this;
    }

    public static FireInspectionData create() {
        return new FireInspectionData();
    }

}
