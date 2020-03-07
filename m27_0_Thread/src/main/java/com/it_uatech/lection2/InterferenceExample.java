package com.it_uatech.lection2;

/**
 * @author v.chibrikov
 */
class InterferenceExample {

    void start() throws InterruptedException {

        CountedThread thread1 = new CountedThread();
        CountedThread thread2 = new CountedThread();
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Expected: " + CounterSemaphore.HUNDRED_MILLION);
        System.out.println("Result Thread 1: " + thread1.getI());
        System.out.println("Result Thread 2: " + thread2.getI());
    }
}
