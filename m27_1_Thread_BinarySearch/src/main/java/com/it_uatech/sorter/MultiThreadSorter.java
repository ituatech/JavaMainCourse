package com.it_uatech.sorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;

public class MultiThreadSorter {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadSorter.class);

    private static final int CORES = Runtime.getRuntime().availableProcessors();
    static {
        logger.info("Available processors: {}",CORES);
    }

    public <T extends Number> void sort(T[] srcArray, int threadNumber, Comparator<T> comparator) {
        logger.debug("Sort method: source array length {}, thread number {}",srcArray.length,threadNumber);
        if (threadNumber < 1 || threadNumber > 8)
            throw new SortException("Your thread number [" + threadNumber + "] is wrong, must be in interval (0,8]");

        if (4 * threadNumber < srcArray.length) {
            threadProducer(srcArray, threadNumber, comparator);
        } else {
            sortArray(srcArray, comparator);
        }
        logger.debug("###########################################################################");
    }

    private <T extends Number> void sortArray(T[] srcArray, Comparator<T> comparator) {
        Arrays.sort(srcArray, comparator);
    }

    <T extends Number> void threadProducer(T[] srcArray, int threadNumber, Comparator<T> comparator) {
        logger.debug("{} - Thread producer method: source array length {}, thread number {}",Thread.currentThread().getName(),srcArray.length,threadNumber);
        if (threadNumber > 1) {
            int[] intervalNodePoints = new int[threadNumber];
            int srcArrayLength = srcArray.length;

            int singleInterval = srcArrayLength / threadNumber;
            for (int i = 0; i < threadNumber; i++) {
                if ((threadNumber - i) > 1) {
                    intervalNodePoints[i] = singleInterval * (i + 1);
                } else intervalNodePoints[i] = srcArrayLength;
            }
            logger.debug("{} - Interval node points: {}",Thread.currentThread().getName(),Arrays.toString(intervalNodePoints));
            int firstPartThreadNumber = threadNumber / 2;
            int secondPartThreadNumber = threadNumber - firstPartThreadNumber;
            T[] firstArrayPart = Arrays.copyOfRange(srcArray,0, intervalNodePoints[firstPartThreadNumber - 1]);
            logger.debug("{} - First part array length: {}",Thread.currentThread().getName(),firstArrayPart.length);
            T[] secondArrayPart = Arrays.copyOfRange(srcArray, intervalNodePoints[firstPartThreadNumber - 1],intervalNodePoints[intervalNodePoints.length - 1]);
            logger.debug("{} - Second part array length: {}",Thread.currentThread().getName(),secondArrayPart.length);

            try {
                Thread firstThread = new Thread(() -> threadProducer(firstArrayPart, firstPartThreadNumber, comparator));
                Thread secondThread = new Thread(() -> threadProducer(secondArrayPart, secondPartThreadNumber, comparator));
                firstThread.start();
                secondThread.start();

                firstThread.join();
                secondThread.join();

                merge(srcArray, firstArrayPart, secondArrayPart, comparator);
            } catch (InterruptedException ex) {
                throw new SortException("", ex);
            }

        } else sortArray(srcArray, comparator);
    }

    <T extends Number> void merge(T[] srcArray, T[] firstArrayPart, T[] secondArrayPart, Comparator<T> comparator) {
        logger.debug("{} - call merge method",Thread.currentThread().getName());
        if ((firstArrayPart.length + secondArrayPart.length) != srcArray.length)
            throw new SortException("Source array length: " + srcArray.length + " , not equals to sum of parts array length: " + firstArrayPart.length + " + " + secondArrayPart.length);

        int firstPartArrayIndex = 0;
        int secondPartArrayIndex = 0;
        for (int i = 0; i < srcArray.length; i++) {
            if (firstPartArrayIndex >= firstArrayPart.length & secondPartArrayIndex < secondArrayPart.length) {
                srcArray[i] = secondArrayPart[secondPartArrayIndex];
                secondPartArrayIndex++;
            } else if (secondPartArrayIndex >= secondArrayPart.length & firstPartArrayIndex < firstArrayPart.length) {
                srcArray[i] = firstArrayPart[firstPartArrayIndex];
                firstPartArrayIndex++;
            } else if (firstPartArrayIndex < firstArrayPart.length & secondPartArrayIndex < secondArrayPart.length) {
                int comparingResult = comparator.compare(firstArrayPart[firstPartArrayIndex], secondArrayPart[secondPartArrayIndex]);

                if (comparingResult == 0) {
                    srcArray[i] = firstArrayPart[firstPartArrayIndex];
                    ++i;
                    srcArray[i] = secondArrayPart[secondPartArrayIndex];
                    firstPartArrayIndex++;
                    secondPartArrayIndex++;
                } else if (comparingResult > 0) {
                    srcArray[i] = secondArrayPart[secondPartArrayIndex];
                    secondPartArrayIndex++;
                } else {
                    srcArray[i] = firstArrayPart[firstPartArrayIndex];
                    firstPartArrayIndex++;
                }
            }

            if ((firstPartArrayIndex + secondPartArrayIndex) > srcArray.length)
                throw new SortException("Index exception: " + firstPartArrayIndex + " + " + secondPartArrayIndex + " > " + srcArray.length);
        }
        if ((firstPartArrayIndex + secondPartArrayIndex) != srcArray.length)
            throw new SortException("Index exception: " + firstPartArrayIndex + " + " + secondPartArrayIndex + " not equals to " + srcArray.length);
    }
}
