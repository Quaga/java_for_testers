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
}
