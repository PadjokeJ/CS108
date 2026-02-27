package dev.padjokej.week02;

public class Rotated<C> implements Image<C> {
    private final Image<C> image;
    private final double angle;
    private final double cosA, sinA;

    public Rotated(Image<C> image, double angle) {
        this.image = image;
        this.angle = Math.toRadians(angle);

        sinA = Math.sin(this.angle);
        cosA = Math.cos(this.angle);
    }

    private double getAngle() {
        return angle;
    }

    @Override
    public C apply(double x, double y) {
        double xprime = x * cosA + y * sinA;
        double yprime = y * cosA - x * sinA;

        return image.apply(xprime, yprime);
    }

}
