package com.it_uatech;

import com.it_uatech.GC_Listener.GCNotification;
import com.it_uatech.agent2.AgentMemoryCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * For 64 bit VMs there are options:
 * 1) Using compressed pointers via -XX:+UseCompressedOops (enabled by default on Java 6)
 * 2) Not using compressed pointers via -XX:-UseCompressedOops
 *
 * This is after Java 6 memory model. In a 32bit JVM, the Shallow size of an object is:
 * 8 bytes (object header) + total of all instance variables + padding (optional)
 * If the first 2 terms don't add upto a multiple of 8 there will be padding.
 * In a 64 bit JVM, the Shallow size is: 16 bytes (object header) + total of all instance variables + padding (optional)
 *
 * VM options -Xmx512m -Xms512m
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 *
 * For Object size checking 1 or 2: 1) ClassLayout.parseClass(MyClass.class).toPrintable(); 2) Agent.jar (VM Option -javaagent:Agent.jar)
 */

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    //array[i] = new Object();
    //array[i] = new String(""); //String pool
    //array[i] = new String(new char[0]); //without String pool
    //array[i] = new MyClass();


    public static void main(String[] args) throws Exception {

        GCNotification.startGC();

        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        char value[] = {'e','d','e','d'};


        MemoryCheck memoryCheck = new MemoryCheck(10_000_000);
        memoryCheck.setUp();

        memoryCheck.measurement(()->new Object(),Object.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new String(value),String.class); //without String pool
        //logger.info("Object size with Agent.jar: "+String.valueOf(AgentMemoryCounter.getSize(new String(value))));  //with Agent.jar
        memoryCheck.clean();

        memoryCheck.measurement(()->new String("aed"),String.class); //String pool
        memoryCheck.clean();

        memoryCheck.measurement(()->new String(new char[]{'a'}),String.class); //without String pool
        memoryCheck.clean();

        memoryCheck.measurement(()->new MyClass(),MyClass.class);
        //logger.info(ClassLayout.parseClass(MyClass.class).toPrintable());  //with ClassLayout library
        memoryCheck.clean();

        //Array check
        memoryCheck.measurement(()->new int[0],int.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new int[10],int.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new int[20],int.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new int[30],int.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new int[40],int.class);
        memoryCheck.clean();

        memoryCheck.measurement(()->new int[50],int.class);
        memoryCheck.clean();
    }


}
