package ru.qa.geometry.figures;

public class Rectangle {
    private double a;
    private double b;
    public Rectangle(double a, double b) {
        if (a < 0 || b < 0){
            throw new IllegalArgumentException("rect side should be non-negative");
        }
        this.a = a;
    }

    public static void printRectangleArea(double a, double b) {
        System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + rectangleArea(a, b));
    }

    private static double rectangleArea(double a, double b) {
        return a * b;
    }
}
