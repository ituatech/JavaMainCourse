package com.it_uatech.gc_logging;

/**
 * For logging every 60 s
 */
public class PeriodInfo{
    private final long period = 60_000L;  // 1 minute
    private long duration;
    private int gcCount;

    public long getPeriod(){
        return period;
    }

    public long getDuration() {
        return duration;
    }

    public int getGcCount() {
        return gcCount;
    }

    public void cleanGCInfo(){
        duration = 0L;
        gcCount = 0;
    }

    public void increaseValues(long duration){
        this.duration += duration;
        gcCount++;
    }
}
