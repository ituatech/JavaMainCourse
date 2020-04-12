package com.it_uatech.server;

import com.it_uatech.api.service.FrontendService;
import com.it_uatech.api.service.MessageSystemContext;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket
public class StatisticWebSocket {

    private  static final Logger logger = LoggerFactory.getLogger(StatisticWebSocket.class);

    private final MessageSystemContext msgSystemCtx;

    public StatisticWebSocket(MessageSystemContext msgSystemCtx) {
        this.msgSystemCtx = msgSystemCtx;
        logger.info("Websocket created");
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        logger.info("Received message: {}",message);
        FrontendService front = (FrontendService) msgSystemCtx.getMessageSystem().getAddresseeMap().get(msgSystemCtx.getFrontAddress());;
        front.handleRequest(Long.valueOf(message), session);
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
