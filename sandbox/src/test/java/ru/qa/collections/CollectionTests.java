package ru.qa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTests {

    @Test
    void arrayTests(){
//        var array = new String[]{"a", "b", "c"};
        var array = new String[3];
        array[0] = "a";
        Assertions.assertEquals("a", array[0]);

        array[0] = "d";
        Assertions.assertEquals("d", array[0]);
    }

    @Test
    void listTests(){
        var list2 = List.of("a", "b", "c"); //non modifiable
        var list = new ArrayList<String>(); //modifiable
        var list3 = new ArrayList<>(List.of("a", "b", "c")); //modifiable
        Assertions.assertEquals(0, list.size());

        list.add("a");
        list.add("b");
        list.add("c");
        Assertions.assertEquals("b", list.get(1));

        list.set(0, "d");
    }
}
