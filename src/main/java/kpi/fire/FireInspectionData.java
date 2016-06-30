package kpi.fire;

import java.util.Arrays;

/**
 * Created by Vitaly Sharapov on 30-Jun-16.
 */
public class FireInspectionData {

    private double volume;
    private double[] apertureHeights;
    private double[] solidtureSpaces;
    private double[] aperMaterialLoads;
    private double[] reducedApertureHeights;
    private double height;

    private FireInspectionData() {
        this.volume = 0;
        this.aperMaterialLoads = new double[0];
        this.apertureHeights = new double[0];
        this.solidtureSpaces = new double[0];
        this.reducedApertureHeights = new double[0];
        this.height = 0;
    }

    public double getVolume() {
        return volume;
    }

    public double[] getApertureSpaces() {
        return aperMaterialLoads;
    }

    public double[] getApertureHeights() {
        return apertureHeights;
    }

    public double[] getSolidMaterialLoads() {
        return solidtureSpaces;
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
        this.aperMaterialLoads = apertureSpaces;
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
        this.solidtureSpaces = solidMaterialLoads;
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
