package gio;

public class CorrectionCoordinate {
    private double lat;
    private double longe;
    private double distance;

    public CorrectionCoordinate(double lat, double longe, double distance) {
        this.lat = lat;
        this.longe = longe;
        this.distance = distance;
    }

    double[] getCoordinate() {
        return new double[]{this.lat, this.longe};
    }

    double getDistance() {
        return this.distance;
    }
}

