package kpi.fire;

import java.util.stream.DoubleStream;

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

    public double getTotalApertureSpace() {
        return DoubleStream.of(apertureSpaces).sum();
    }

    public double[] getApertureHeights() {
        return apertureHeights;
    }

    public double[] getSolidMaterialsLoads() {
        return solidMaterialsLoads;
    }

    public double getReducedH() {
        reducedH = 0.0;

        for (int i = 0; i < apertureSpaces.length; i++) {
            reducedH += apertureSpaces[i] * apertureHeights[i];
        }

        return reducedH / getTotalApertureSpace();
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
