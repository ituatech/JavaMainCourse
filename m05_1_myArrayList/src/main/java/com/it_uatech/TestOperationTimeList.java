package com.it_uatech;

import java.util.*;

public class TestOperationTimeList {
    private static final int NUMBER_OF_MEASUREMENT = 1;

    public static void main(String[] args) {

        int min = 0;
        int max = 9_999_999;

        List<Integer> collection = new LinkedList<>();

        for (int i = min; i < (max +1); i++){
            collection.add(i);
        }

        //Collections.frequency(example, min);
        //Collections.min(example);
        //Collections.binarySearch((List<Integer>) example, min);
        //Collections.rotate((List<Integer>)example,10);
        //Collections.reverse((List<Integer>)example);
        //boolean a = example.contains(max);


        String time = timeMeasure(
                ()->collection.contains(4_000_000)
        );

        System.out.println("Measurement for contains: "+time);

        String time1 = timeMeasure(
                ()->collection.remove(4_000_000)
        );

        System.out.println("Measurement for remove: "+time1);

        String time2 = timeMeasure(
                ()->collection.add(10000)
        );

        System.out.println("Measurement for add in the end: "+time2);

        String time3 = timeMeasure(
                ()->collection.add(max-2500000,10000)
        );

        System.out.println("Measurement for add in the middle: "+time3);

        String time4 = timeMeasure(
                ()->collection.get(min+1000000)
        );

        System.out.println("Measurement for get index element: "+time4);
    }

    private static String timeMeasure(Runnable runnable){
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_MEASUREMENT; i++){
            runnable.run();
        }
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime)/NUMBER_OF_MEASUREMENT;

        return ("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
