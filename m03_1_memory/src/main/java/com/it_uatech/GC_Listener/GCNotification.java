package com.it_uatech.GC_Listener;


import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GCNotification {
    private static final Logger logger = LoggerFactory.getLogger(GCNotification.class);
    private final CountDownLatch doneSignal = new CountDownLatch(2);
    private List<Runnable> registeredListeners = new ArrayList<>();

    private GCNotification() {
        installGCMonitoring();
    }

    public static void startGC() {
        GCNotification gcNotification = new GCNotification();
        System.gc();
        try {
            gcNotification.doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            gcNotification.registeredListeners.forEach(Runnable::run);
        }
    }

    private void installGCMonitoring() {
        List<GarbageCollectorMXBean> garbageCollectorsList = ManagementFactory.getGarbageCollectorMXBeans();
        logger.info("Garbage collectors are used in JVM:");
        for (GarbageCollectorMXBean gcBean : garbageCollectorsList) {
            logger.info(gcBean.getName());
        }

        for (GarbageCollectorMXBean gcBean : garbageCollectorsList) {
            // receive the notification emitted by a GarbageCollectorMXBean and set to notif
            NotificationEmitter emitter = (NotificationEmitter) gcBean;

            NotificationListener notificationListener = new NotificationListener() {
                @Override
                public void handleNotification(Notification notification, Object handback) {

                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                        // retrieve the garbage collection notification information
                        CompositeData cd = (CompositeData) notification.getUserData();
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);

                        long duration = info.getGcInfo().getDuration();
                        String gctype = info.getGcAction();

                        logger.info(gctype + ": - "
                                + info.getGcInfo().getId() + ", "
                                + info.getGcName()
                                + " (from " + info.getGcCause() + ") " + duration + " milliseconds");

                        if (info.getGcCause().equals("System.gc()")) {
                            doneSignal.countDown();
                            if(doneSignal.getCount()>0) {
                                System.gc();
                            }
                        }
                    }
                }
            };
            emitter.addNotificationListener(notificationListener, null, null);
            registeredListeners.add(() -> {
                try {
                    emitter.removeNotificationListener(notificationListener);
                } catch (ListenerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}


