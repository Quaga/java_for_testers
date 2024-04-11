package ru.qa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {
    @Test
    void canCalculateArea(){
        var t = new Triangle(13.0, 14.0, 15.0);
        Assertions.assertEquals(84.0, t.area());
    }

    @Test
    void canCalculatePerimeter(){
        var t = new Triangle(4.0, 5.0, 6.0);
        Assertions.assertEquals(15.0, t.perimeter());
    }

    @Test
    void cannotCreateTriangleWithNegativeSide(){
        try {
            new Triangle(-5.0, 3.0, 4.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
        try {
            new Triangle(5.0, -3.0, 4.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
        try {
            new Triangle(5.0, 3.0, -4.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
    }

    @Test
    void cannotCreateTriangleWithTooLongSide(){
        try {
            new Triangle(1.0, 2.0, 3.1);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
        try {
            new Triangle(1.0, 3.1, 2.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
        try {
            new Triangle(3.1, 2.0, 1.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            // OK
        }
    }
}
