package kpi.fire.domain;

public class Material {

    private String name;
    private double fireLoad;
    private double airToBurn;
    private double minBurnTemperature;
    private double averageBurnSpeed;

    public Material(String name, double fireLoad, double airToBurn, double minBurnTemperature, double averageBurnSpeed) {
        this.name = name;
        this.fireLoad = fireLoad;
        this.airToBurn = airToBurn;
        this.minBurnTemperature = minBurnTemperature;
        this.averageBurnSpeed = averageBurnSpeed;
    }

    public String getName() {
        return name;
    }

    public double getFireLoad() {
        return fireLoad;
    }

    public double getAirToBurn() {
        return airToBurn;
    }

    public double getMinBurnTemperature() {
        return minBurnTemperature;
    }

    public double getAverageBurnSpeed() {
        return averageBurnSpeed;
    }

    public Material setFireLoad(double fireLoad) {
        this.fireLoad = fireLoad;
        return this;
    }
}
