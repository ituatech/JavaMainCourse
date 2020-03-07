package com.it_uatech.lection1;

class ThreadInfo {

    void print() throws InterruptedException {
        //get an object ot the main thread
        Thread mainThread = Thread.currentThread();
        System.out.println("Thread name: " + mainThread.getName());
        //set name to the thread
        mainThread.setName("Main Thread");
        System.out.println("Thread name: " + mainThread.getName());
        //get priority of the main thread
        System.out.println("Main Thread priority: " + mainThread.getPriority());

        //create new thread with runnable lambda
        Thread thread2 = new Thread(() -> {
            Thread.currentThread().setName("Thread 2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from the thread: " + Thread.currentThread().getName());
        }
        );

        //set daemon
        //thread2.setDaemon(true);

        thread2.start();

        thread2.setPriority(10);

        //join to a the main thread
        //thread2.join();

        //get thread state
        System.out.println("Main Thread state: " + mainThread.getState());
        System.out.println("Thread 2 state: " + thread2.getState());
    }
}
