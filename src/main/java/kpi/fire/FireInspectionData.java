package kpi.fire;

public class FireInspectionData {

    private double volume;
    private double[] apertureSpaces;
    private double[] apertureHeights;
    private double[] solidMaterialsLoads;
    private double reducedH;
    private double height;

    private FireInspectionData() {
        this.volume = 0;
        this.apertureSpaces = new double[0];
        this.apertureHeights = new double[0];
        this.solidMaterialsLoads = new double[0];
        this.reducedH = 0;
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
        return reducedH;
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

    public FireInspectionData setSolidMaterialsLoads(double[] solidMaterialsLoads) {
        this.solidMaterialsLoads = solidMaterialsLoads;
        return this;
    }

    public FireInspectionData setReducedH(double reducedH) {
        this.reducedH = reducedH;
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
