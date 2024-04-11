package ru.qa.geometry.figures;

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
}
