package kpi.fire.domain;

import static java.lang.Math.sqrt;
import static kpi.fire.util.MathUtils.dotProduct;
import static kpi.fire.util.MathUtils.sum;

public class FireInspectionData {

    private double volume;
    private ApertureData apertureData;
    private MaterialData materialData;
    private double height;
    private double lowestWoodBurnHeat;
    private  double initialVolumeAverageTemperature;
    private  double initialAverageOverlappingAreaTemperature;

    private FireInspectionData() {
        this.apertureData = new ApertureData();
        this.materialData = new MaterialData();
    }

    public double getVolume() {
        return volume;
    }

    public ApertureData getApertureData() {
        return apertureData;
    }

    public MaterialData getMaterialData() {
        return materialData;
    }

    public double getReducedH() {
        return dotProduct(apertureData.getApertureSpaces(), apertureData.getApertureHeights())
                / sum(apertureData.getApertureSpaces());
    }

    public double getHeight() {
        return height;
    }

    public double getFloorArea() {
        return volume / height;
    }

    public double getLowestWoodBurnHeat() {
        return lowestWoodBurnHeat;
    }

    public double getInitialVolumeAverageTemperature() {
        return initialVolumeAverageTemperature;
    }

    public double getInitialAverageOverlappingAreaTemperature() {
        return initialAverageOverlappingAreaTemperature;
    }

    public FireInspectionData setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public FireInspectionData setApertureData(ApertureData apertureData) {
        this.apertureData = apertureData;
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

    public FireInspectionData setLowestWoodBurnHeat(double lowestWoodBurnHeat) {
        this.lowestWoodBurnHeat = lowestWoodBurnHeat;
        return this;
    }

    public FireInspectionData setInitialVolumeAverageTemperature(double initialVolumeAverageTemperature) {
        this.initialVolumeAverageTemperature = initialVolumeAverageTemperature;
        return this;
    }

    public FireInspectionData setInitialAverageOverlappingAreaTemperature(double initialAverageOverlappingAreaTemperature) {
        this.initialAverageOverlappingAreaTemperature = initialAverageOverlappingAreaTemperature;
        return this;
    }

    public static FireInspectionData create() {
        return new FireInspectionData();
    }

    public double computeFireDuration() {
        double firstAuxiliarySum = dotProduct(materialData.getSolidMaterialsLoads(), materialData.getMinBurnTemperatures());

        double totalApertureSpace = sum(apertureData.getApertureSpaces());

        double totalSolidMaterialLoads = sum(materialData.getSolidMaterialsLoads());

        double secondAuxiliarySum = dotProduct(materialData.getAverageBurnSpeeds(), materialData.getSolidMaterialsLoads());

        return firstAuxiliarySum * 2.4 * totalSolidMaterialLoads
                / (6285 * totalApertureSpace * sqrt(getReducedH()) * secondAuxiliarySum);
    }

    public double limitFireDuration(double fireDuration) {
        if (fireDuration < 0.15) {
            return 0.15;
        }
        if (fireDuration > 1.22) {
            return 1.22;
        }
        return fireDuration;
    }

}
