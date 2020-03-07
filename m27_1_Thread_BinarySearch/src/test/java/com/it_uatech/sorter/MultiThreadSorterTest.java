package com.it_uatech.sorter;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MultiThreadSorterTest {

    private static final int ARRAY_SIZE = 100_000;

    private static Integer[] array1 = new Integer[ARRAY_SIZE];
    private static Integer[] array2 = new Integer[ARRAY_SIZE];
    private static Integer[] array3 = new Integer[ARRAY_SIZE];

    private static MultiThreadSorter sorter = new MultiThreadSorter();

    @BeforeClass
    public static void setUp(){
        Random random = new Random();

        for (int i = 0; i < ARRAY_SIZE; i++){
            array1[i] = ARRAY_SIZE - i;
            array2[i] = random.nextInt(ARRAY_SIZE + 1);
            array3[i] = 15;
        }
    }

    @Test
    public void shouldSortArraysByAllThreadsNumber(){
        // sort array type 1
        for (int thread = 1; thread <= 8; thread++) {
            Integer[] sortedArray = Arrays.copyOf(array1, ARRAY_SIZE);
            Integer[] expectedArray = Arrays.copyOf(array1, ARRAY_SIZE);

            Arrays.sort(expectedArray, Integer::compareTo);
            sorter.sort(sortedArray, thread, Integer::compareTo);

            assertArrayEquals("Sort array type 1 by thread number: "+thread,expectedArray, sortedArray);
        }
        // sort array type 2
        for (int thread = 1; thread <= 8; thread++) {
            Integer[] sortedArray = Arrays.copyOf(array2, ARRAY_SIZE);
            Integer[] expectedArray = Arrays.copyOf(array2, ARRAY_SIZE);

            Arrays.sort(expectedArray, Integer::compareTo);
            sorter.sort(sortedArray, thread, Integer::compareTo);

            assertArrayEquals("Sort array type 2 by thread number: "+thread,expectedArray, sortedArray);
        }
        // sort array type 3
        for (int thread = 1; thread <= 8; thread++) {
            Integer[] sortedArray = Arrays.copyOf(array3, ARRAY_SIZE);
            Integer[] expectedArray = Arrays.copyOf(array3, ARRAY_SIZE);

            Arrays.sort(expectedArray, Integer::compareTo);
            sorter.sort(sortedArray, thread, Integer::compareTo);

            assertArrayEquals("Sort array type 3 by thread number: "+thread,expectedArray, sortedArray);
        }
    }

    @Test
    @Ignore
    public void time(){
        long startTime= 0L;
        long finishTime = 0L;
        Integer[] sortedArray = Arrays.copyOf(array2, ARRAY_SIZE);
        Integer[] expectedArray = Arrays.copyOf(array2, ARRAY_SIZE);
        Integer[] streamArray = Arrays.copyOf(array2, ARRAY_SIZE);

        startTime = System.currentTimeMillis();
        Arrays.sort(expectedArray, Integer::compareTo);
        finishTime = System.currentTimeMillis();
        System.out.println("Sort with 1 thread: " + (finishTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        sorter.sort(sortedArray, 4, Integer::compareTo);
        finishTime = System.currentTimeMillis();
        System.out.println("Sort with N thread: " + (finishTime - startTime) + " ms");

        List<Integer> list = Arrays.asList(streamArray);
        Stream<Integer> stream = list.parallelStream();
        startTime = System.currentTimeMillis();
        stream.sorted(Integer::compareTo).collect(Collectors.toList());
        finishTime = System.currentTimeMillis();
        System.out.println("Sort with stream API: " + (finishTime - startTime) + " ms");
        System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void shouldThrowExceptionByWrongThreadNumber(){
        assertThrows("Thread number < 1",SortException.class,()->sorter.sort(array1, 0, Integer::compareTo));
        assertThrows("Thread number > 8",SortException.class,()->sorter.sort(array1, 9, Integer::compareTo));
    }

    @Test
    public void shouldTrowExceptionIfSrcArrayLengthNotEqualToSumOfPartsArraysLength(){
        assertThrows("Arrays length",SortException.class,()->sorter.merge(new Integer[20],new Integer[9],new Integer[10],Integer::compareTo));
    }

}