package ru.qa.geometry.figures;

public record Square(double side) {
    public Square {
        if (side < 0){
            throw new IllegalArgumentException("sq side should be non-negative");
        }
    }
    public static void printSquareArea(Square s) {
        String text = String.format("Площадь квадрата со стороной %f = %f", s.side, s.area());
        System.out.println(text);
    }

    public static double area(double side) {
        return side * side;
    }

    public double area() {
        return this.side * this.side;
    }

    public double perimeter() {
        return this.side * 4;
    }
}
