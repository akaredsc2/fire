package kpi.fire.domain;

import kpi.fire.util.ArrayUtils;

public class ApertureData {

    private Aperture[] apertures;

    public ApertureData() {
        apertures = new Aperture[0];
    }

    public ApertureData(Aperture[] apertures) {
        this.apertures = apertures;
    }

    public double[] getApertureSpaces() {
        return ArrayUtils.map(apertures, Aperture::getArea);
    }

    public double[] getApertureHeights() {
        return ArrayUtils.map(apertures, Aperture::getHeight);
    }

}
