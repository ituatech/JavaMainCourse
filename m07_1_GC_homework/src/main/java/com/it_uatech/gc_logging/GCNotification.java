package com.it_uatech.gc_logging;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


public class GCNotification {
    private static final Logger logger = LoggerFactory.getLogger(GCNotification.class);
    private static final Timer timer = new Timer();
    private final List<Runnable> registeredListeners = new ArrayList<>();
    private final Instant timeStart;
    private long totalDurationYoung;
    private long totalDurationOld;
    private int counterYoung;
    private int counterOld;
    private PeriodInfo period;

    public GCNotification(PeriodInfo period) {
        this.timeStart = Instant.now();
        this.period = period;
        setUpPeriod();
        setUpListener();
    }

    private void setUpListener() {
        List<GarbageCollectorMXBean> garbageCollectorsList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : garbageCollectorsList) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            NotificationListener notificationListener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    CompositeData cd = (CompositeData) notification.getUserData();
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);
                    notificationLog(info);
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

    private void setUpPeriod(){
       TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
              logger.info("#########################################################");
              logger.info("Info per interval: {} s, GC done: {}, GC duration: {} ms",period.getPeriod()/1000,period.getGcCount(),period.getDuration());
              logger.info("#########################################################");
              period.cleanGCInfo();
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,period.getPeriod());
    }

    private synchronized void notificationLog(GarbageCollectionNotificationInfo info) {
        String gcType = info.getGcAction();
        long duration = info.getGcInfo().getDuration();
        logger.info("{} s - GC Name: {}, GC Action: {}, GC ID: {}, GC Duration: {} ms, GC Couse: {}",
                    Duration.between(timeStart,Instant.now()).getSeconds(),info.getGcName(),gcType,info.getGcInfo().getId(),duration,info.getGcCause());
        if(gcType.equals(MemoryTypes.YOUNG.getGcAction())){
            this.totalDurationYoung += duration;
            counterYoung++;
        }else if(gcType.equals(MemoryTypes.OLD.getGcAction())){
            totalDurationOld += duration;
            counterOld++;
        }
        period.increaseValues(duration);
    }

    public String[] printTotalGCResult(){
        return new String[]{"Total Young GC activities: " + counterYoung + ", Total duration Young: " + totalDurationYoung + " ms",
                "Total Old GC activities: " + counterOld + ", Total duration Old: " + totalDurationOld + " ms",
                "Total app duration: "+Duration.between(timeStart,Instant.now()).getSeconds()+" s"};
    }

    public void removeListeners(){
        registeredListeners.forEach(Runnable::run);
        timer.cancel();
    }
}
