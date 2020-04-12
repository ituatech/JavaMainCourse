package com.it_uatech.server;

import com.it_uatech.api.service.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class StatisticWebSocketCreator implements WebSocketCreator {

    private  static final Logger logger = LoggerFactory.getLogger(StatisticWebSocketCreator.class);
    private MessageSystemContext msgSystemCtx;

    public StatisticWebSocketCreator(MessageSystemContext msgSystemCtx) {
        this.msgSystemCtx = msgSystemCtx;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        StatisticWebSocket socket = new StatisticWebSocket(msgSystemCtx);
        return socket;
    }
}
