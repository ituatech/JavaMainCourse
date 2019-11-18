package com.it_uatech;

import java.util.Arrays;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MemoryCheck {

    private static final Logger logger = LoggerFactory.getLogger(MemoryCheck.class);

    private Object[] arrayOfObjects;
    private int objectsNumber;

    public MemoryCheck() {
        this.objectsNumber = 20_000_000;
    }

    public MemoryCheck(int objectsNumber) {
        this.objectsNumber = objectsNumber;
    }

    protected void setUp() throws InterruptedException {
        long initionMemory = memoryCheck(() -> this.arrayOfObjects = new Object[objectsNumber]);
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        logger.info("References size: {}, Array size: {}",initionMemory,this.arrayOfObjects.length);
    }

    protected void measurement(Supplier<Object> supplier, Class<?> clazz) throws InterruptedException {

        long objectMemory = memoryCheck(() -> {
            for (int i = 0; i < objectsNumber; i++) {
                this.arrayOfObjects[i] = supplier.get();
            }
        });
        logger.info("name:{}, size: {}", clazz.getSimpleName(), Math.round((double) objectMemory / objectsNumber));
    }

    protected void clean() throws InterruptedException {
        this.arrayOfObjects = new Object[objectsNumber];
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
    }

    private long memoryCheck(Runnable runnable) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        runnable.run();
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        long memAfter = runtime.totalMemory() - runtime.freeMemory();

        return memAfter - memBefore;
    }
}
