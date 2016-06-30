package kpi.fire;

/**
 * Created by Vitaly Sharapov on 30-Jun-16.
 */
public class FireInspectionData {

    private double volume;
    private double[] apertureSpaces;
    private double[] apertureHeights;
    private double[] solidMaterialLoads;
    private double[] reducedApertureHeights;
    private double height;

    private FireInspectionData() {
        this.volume = 0;
        this.apertureSpaces = new double[0];
        this.apertureHeights = new double[0];
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

    public double[] getSolidMaterialLoads() {
        return solidMaterialLoads;
    }

    public double[] getReducedApertureHeights() {
        return reducedApertureHeights;
    }

    public double getHeight() {
        return height;
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

    public FireInspectionData setReducedApertureHeights(double[] reducedApertureHeights) {
        this.reducedApertureHeights = reducedApertureHeights;
        return this;
    }

    public FireInspectionData setSolidMaterialLoads(double[] solidMaterialLoads) {
        this.solidMaterialLoads = solidMaterialLoads;
        return this;
    }

    public FireInspectionData setHeight(double height) {
        this.height = height;
        return this;
    }

    public FireInspectionData create() {
        return new FireInspectionData();
    }
}
