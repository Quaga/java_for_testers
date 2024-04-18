package ru.qa.geometry.figures;

import java.util.Arrays;

public class Triangle{
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        if (a < 0 || b < 0 || c < 0){
            throw new IllegalArgumentException("Triangle side should be non-negative");
        }
        if (a+b < c || b+c < a || a+c < b){
            throw new IllegalArgumentException("Triangle inequality theorem is violated");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double area() {
        var s = this.perimeter() / 2;

        return Math.sqrt(s * (s - this.a) * (s - this.b) * (s - this.c));
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle that = (Triangle) o;

        double[] dimensions1 = { this.a, this.b, this.c };
        double[] dimensions2 = { that.a, that.b, that.c };
        Arrays.sort(dimensions1);
        Arrays.sort(dimensions2);
        return Arrays.equals(dimensions1, dimensions2);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
