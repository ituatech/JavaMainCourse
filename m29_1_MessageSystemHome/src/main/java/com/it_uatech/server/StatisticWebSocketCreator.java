package com.it_uatech.server;

import com.it_uatech.api.service.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class StatisticWebSocketCreator implements WebSocketCreator {

    private MessageSystemContext msgSystemCtx;

    public StatisticWebSocketCreator(MessageSystemContext msgSystemCtx) {
        this.msgSystemCtx = msgSystemCtx;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new StatisticWebSocket(msgSystemCtx);
    }
}
