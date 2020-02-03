package com.it_uatech.api.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    private final static long TIME_THRESHOLD_MS = 5;

    private Map<K, CacheElement<K, V>> cacheElements = new LinkedHashMap<>();

    private final int cacheSize;
    private final long lifeTime;
    private final long idleTime;
    private final boolean isEternal;

    private Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int cacheSize, long lifeTime, long idleTime) {
        this.cacheSize = cacheSize > 0 ? cacheSize : 10;
        this.lifeTime = lifeTime > 0 ? lifeTime : 0;
        this.idleTime = idleTime > 0 ? idleTime : 0;
        this.isEternal = (lifeTime == 0 && idleTime == 0);
    }

    @Override
    public void put(CacheElement<K, V> element) {
        if (cacheElements.size() >= cacheSize) {
            K firstKey = cacheElements.keySet().iterator().next();
            cacheElements.remove(firstKey);
        }

        K key = element.getKey();
        cacheElements.put(key, element);

        if(!isEternal) {
            if (lifeTime > 0) {
                TimerTask lifeTask = getTimerTask(key, cacheElement -> cacheElement.getCreationTime() + lifeTime);
                timer.schedule(lifeTask, lifeTime);
            }

            if (idleTime > 0) {
                TimerTask idleTask = getTimerTask(key, cacheElement -> cacheElement.getLastAccessTime() + idleTime);
                timer.schedule(idleTask, idleTime, idleTime);
            }
        }
    }

    @Override
    public CacheElement<K, V> get(K key) {
        CacheElement<K, V> element = cacheElements.get(key);
        if (element != null) {
            element.setLastAccessTime();
            hit++;
        }else {
            miss++;
        }
        return element;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(K key, Function<CacheElement<K, V>, Long> evictTimeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> element = cacheElements.get(key);
                if (element == null || (evictTimeFunction.apply(element) < (element.getCurrentTime() + TIME_THRESHOLD_MS))) {
                    cacheElements.remove(key);
                    this.cancel();
                }
            }
        };
    }
}
