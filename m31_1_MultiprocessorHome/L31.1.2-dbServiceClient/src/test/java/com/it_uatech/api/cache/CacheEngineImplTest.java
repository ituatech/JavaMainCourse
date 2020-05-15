package com.it_uatech.api.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CacheEngineImplTest {

    private CacheEngineImpl<String,String> cacheEngine;
    private long lifeTime;
    private long idleTime;

    @BeforeEach
    public void setUp() {
        lifeTime = 1000;
        idleTime = 300;
        cacheEngine = new CacheEngineImpl<>(2, lifeTime, idleTime);
    }

    @Test
    public void shouldPutCacheElementInCacheAndRetrieveIt() {
        CacheElement<String,String> element = new CacheElement<>("asd", "asd");
        cacheEngine.put(element);
        assertEquals(element, cacheEngine.get("asd"));
    }

    @Test
    public void shouldCheckMaxCacheSizeBeforeAddingOfElementAndEvictTheFirstElementIfDefinedSizeReached() {
        CacheElement<String,String> element1 = new CacheElement<>("asd", "asd");
        CacheElement<String,String> element2 = new CacheElement<>("qwe", "qwe");
        CacheElement<String,String> element3 = new CacheElement<>("yxc", "yxc");
        cacheEngine.put(element1);
        cacheEngine.put(element2);
        assertEquals(element1, cacheEngine.get("asd"));
        assertEquals(element2, cacheEngine.get("qwe"));
        cacheEngine.put(element3);
        assertEquals(element3, cacheEngine.get("yxc"));
        assertNull(cacheEngine.get("asd"));
    }

    @Test
    public void shouldStartTimerTaskWithLifeTimeAndEvictElementAfterThat() throws InterruptedException {
        CacheEngine<String,String> engine = new CacheEngineImpl<>(2,lifeTime,0);
        CacheElement<String,String> element = new CacheElement<>("asd", "asd");
        engine.put(element);
        Thread.sleep(lifeTime - lifeTime / 2);
        assertEquals(element, engine.get("asd"));
        Thread.sleep(lifeTime);
        assertNull(engine.get("asd"));
        engine.dispose();
    }

    @Test
    public void shouldStartTimerTaskWithIdleTimeAndEvictElementAfterThat() throws InterruptedException {
        CacheEngine<String,String> engine = new CacheEngineImpl<>(2,0,idleTime);
        CacheElement<String,String> element = new CacheElement<>("asd", "asd");
        engine.put(element);
        Thread.sleep(idleTime - idleTime / 2);
        assertEquals(element, engine.get("asd"));
        Thread.sleep(idleTime - idleTime / 2);
        assertEquals(element, engine.get("asd"));
        Thread.sleep(idleTime - idleTime / 2);
        assertEquals(element, engine.get("asd"));
        Thread.sleep(2*idleTime);
        assertNull(engine.get("asd"));
        engine.dispose();
    }

    @Test
    public void shouldGetHitAndMiss() throws InterruptedException {
        CacheElement<String,String> element1 = new CacheElement<>("asd", "asd");
        CacheElement<String,String> element2 = new CacheElement<>("qwe", "qwe");
        cacheEngine.put(element1);
        cacheEngine.put(element2);
        cacheEngine.get("asd");
        cacheEngine.get("qwe");
        assertEquals(2,cacheEngine.getHitCount());
        assertEquals(0,cacheEngine.getMissCount());
        Thread.sleep(idleTime+lifeTime);
        cacheEngine.get("asd");
        cacheEngine.get("qwe");
        assertEquals(2,cacheEngine.getHitCount());
        assertEquals(2,cacheEngine.getMissCount());
    }

    @AfterEach
    public void tearDown(){
        cacheEngine.dispose();
    }
}