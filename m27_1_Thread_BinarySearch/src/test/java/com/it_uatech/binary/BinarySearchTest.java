package com.it_uatech.binary;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class BinarySearchTest {

    private static List<Integer> list;
    private static BinarySearch search;
    private static final int ARRAY_ELEMENTS = 1000_000;

    @BeforeClass
    public static void generateListOfNumbers(){
        list = new ArrayList<>();
        for (int i = 0; i < ARRAY_ELEMENTS; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        search = new BinarySearch();
    }

    @Test
    public void shouldFindAnElement() {
        int a = (int) (Math.random() * ARRAY_ELEMENTS);
        int searched = search.search(list,a, Integer::compareTo).get();
        assertEquals(a,searched);

        int b = 0;
        searched = search.search(list,b, Integer::compareTo).get();
        assertEquals(b,searched);

        int c = ARRAY_ELEMENTS - 1;
        searched = search.search(list,c, Integer::compareTo).get();
        assertEquals(c,searched);
    }

    @Test
    public void shouldRetrieveNullIfNothingFound(){
        int a = ARRAY_ELEMENTS + 12222;
        Optional<Integer> optionalInteger = search.search(list,a, Integer::compareTo);
        assertTrue(optionalInteger.isEmpty());

        int b = -567;
        optionalInteger = search.search(list,b, Integer::compareTo);
        assertTrue(optionalInteger.isEmpty());
    }

    @Test(expected = SearchException.class)
    public void shouldThrowExeptionByEmptyList(){
        List<Integer> list = new ArrayList<>();
        int a = 55;
        search.search(list,a,Integer::compareTo);
    }
}