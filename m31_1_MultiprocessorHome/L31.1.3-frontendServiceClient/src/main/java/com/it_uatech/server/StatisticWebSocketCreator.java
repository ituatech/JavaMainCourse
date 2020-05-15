package com.it_uatech.server;

import com.it_uatech.api.FrontendServiceUser;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class StatisticWebSocketCreator implements WebSocketCreator {

    private FrontendServiceUser serviceUser;

    public StatisticWebSocketCreator(FrontendServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new StatisticWebSocket(serviceUser);
    }
}
