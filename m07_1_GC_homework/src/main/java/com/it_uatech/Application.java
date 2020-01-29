package com.it_uatech;

import com.it_uatech.gc_logging.GCNotification;
import com.it_uatech.gc_logging.PeriodInfo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.management.ManagementFactory;

/**
 * -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 * -Xms64m  -Xmx64m  -XX:MaxMetaspaceSize=32m
 * -Dcom.sun.management.jmxremote.port=15000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps
 * -verbose:gc
 * -Xlog:gc*:file=./logs/gc_pid__%t_%p.log
 * -XX:+UseSerialGC -XX:+UseParallelGC -XX:+UseConcMarkSweepGC -XX:+UseG1GC
 * <p>
 * jps -- list vms or ps -e | grep java
 * jstack <pid> >> threaddumps.log -- get dump from pid
 * jinfo -- list VM parameters
 * jhat, jvisualvm-- analyze heap dump
 **/

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        PeriodInfo periodInfo = new PeriodInfo();
        GCNotification gcNotification = new GCNotification(periodInfo);
        logger.info("GC work test, app PID: {}", ManagementFactory.getRuntimeMXBean().getPid());
        logger.info("JVM List arguments: {}", ManagementFactory.getRuntimeMXBean().getInputArguments());
        try {
            BuildMemoryLeak.getInstance().appStart();
        } catch (OutOfMemoryError error) {
            gcNotification.removeListeners();
            logger.error("OOM has occured!");
            String[] result = gcNotification.printTotalGCResult();
            for (String generation : result) {
                logger.info(generation);
            }
        }
    }
}
