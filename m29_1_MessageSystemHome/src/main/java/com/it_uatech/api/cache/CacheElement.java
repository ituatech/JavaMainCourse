package com.it_uatech.api.cache;

import java.lang.ref.SoftReference;

public class CacheElement<K,V> {
    private final K key;
    private final SoftReference<V> value;

    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(K key, V value) {
        this.key = key;
        this.value = new SoftReference<>(value);
        creationTime = getCurrentTime();
        lastAccessTime = getCurrentTime();
    }

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value.get();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime() {
        lastAccessTime = getCurrentTime();
    }
}
