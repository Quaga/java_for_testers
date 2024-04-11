package ru.qa.geometry.figures;

import java.util.Objects;

public class Rectangle {
    private double a;
    private double b;
    public Rectangle(double a, double b) {
        if (a < 0 || b < 0){
            throw new IllegalArgumentException("rect side should be non-negative");
        }
        this.a = a;
        this.b = b;
    }

    public static void printRectangleArea(double a, double b) {
        System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + rectangleArea(a, b));
    }

    private static double rectangleArea(double a, double b) {
        return a * b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return (Double.compare(this.a, rectangle.a) == 0 && Double.compare(this.b, rectangle.b) == 0)
                || (Double.compare(this.b, rectangle.a) == 0 && Double.compare(this.a, rectangle.b) == 0);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(a, b);
        return 1;
    }
}
