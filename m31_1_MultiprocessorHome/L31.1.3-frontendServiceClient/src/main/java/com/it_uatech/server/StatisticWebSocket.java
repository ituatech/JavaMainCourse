package com.it_uatech.server;

import com.it_uatech.api.FrontendServiceUser;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

@WebSocket
public class StatisticWebSocket {

    private  static final Logger logger = LoggerFactory.getLogger(StatisticWebSocket.class);

    private final FrontendServiceUser serviceUser;

    public StatisticWebSocket(FrontendServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        logger.info("Websocket created");
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        logger.info("Received message: {}",message);
        int randomGeneratedKey = ThreadLocalRandom.current().nextInt(1000_000, 1000_000_000);
        serviceUser.getWebSocketSessionMap().put(randomGeneratedKey,session);
        if(!message.isEmpty()) {
            serviceUser.handleRequest(Long.valueOf(message), randomGeneratedKey);
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        logger.info("Sending message: socket opened");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.info("Sending message: socket closed");
    }

}
