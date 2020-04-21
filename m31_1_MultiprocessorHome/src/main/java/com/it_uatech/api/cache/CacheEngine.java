package com.it_uatech.api.cache;

public interface CacheEngine <K,V> {

    void put(CacheElement<K, V> element);

    CacheElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    int getSize();

    void dispose();
}
