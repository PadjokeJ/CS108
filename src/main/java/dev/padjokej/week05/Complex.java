package dev.padjokej.week05;

public record Complex(double a, double b) {
    public static final Complex ZERO = new Complex(0, 0);

    public static Complex multiply(Complex c1, Complex c2) {
        double a = c1.a() * c2.a() - c1.b() * c2.b();
        double b = c1.a() * c2.b() + c2.a() * c1.b();
        return new Complex(a, b);
    }

    public static Complex square(Complex c) {
        return multiply(c, c);
    }

    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.a() + c2.a(), c1.b() + c2.b());
    }

    public static <C extends Number> Complex scale(Complex c, C s) {
        return new Complex(c.a() * s.doubleValue(), c.b() * s.doubleValue());
    }

    public double module() {
        return Math.sqrt(a() * a() + b() * b());
    }
}
