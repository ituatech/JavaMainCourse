package com.it_uatech.api;

import com.it_uatech.app.FrontendServiceUserMS;
import org.eclipse.jetty.websocket.api.Session;

import java.util.EnumMap;
import java.util.concurrent.ConcurrentMap;

public interface FrontendServiceUser extends FrontendServiceUserMS {

    ConcurrentMap<Integer, Session> getWebSocketSessionMap();

    EnumMap<CacheParameter, Integer> getCacheParameterMap();
}
