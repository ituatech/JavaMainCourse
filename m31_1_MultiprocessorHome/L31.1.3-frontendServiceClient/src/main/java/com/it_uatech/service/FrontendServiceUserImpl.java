package com.it_uatech.service;

import com.google.gson.Gson;
import com.it_uatech.addressedMessages.MessageGetCacheHitMiss;
import com.it_uatech.addressedMessages.MessageGetCacheSize;
import com.it_uatech.addressedMessages.MessageGetUserById;
import com.it_uatech.api.CacheParameter;
import com.it_uatech.api.FrontendServiceUser;
import com.it_uatech.app.MessageToFrontUser;
import com.it_uatech.channel.SocketMessageTransferClient;
import com.it_uatech.dto.UserDTO;
import com.it_uatech.messageSystem.AddressedMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendServiceUserImpl implements FrontendServiceUser {

    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceUserImpl.class);

    private final ExecutorService executorService;

    private final Gson gson;
    private final SocketMessageTransferClient worker;
    private final ConcurrentMap<Integer, Session> webSocketSessionMap;
    private final EnumMap<CacheParameter, Integer> cacheParameterMap = new EnumMap<>(CacheParameter.class);

    public FrontendServiceUserImpl(SocketMessageTransferClient worker, Gson gson) {
        this.worker = worker;
        this.gson = gson;
        this.executorService = Executors.newSingleThreadExecutor();
        this.webSocketSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void handleRequest(Long id, int key) {
        logger.info("Frontend handle request for id: {}", id);
        AddressedMessage msg = new MessageGetUserById(worker.getOwnAddress(), worker.getCompanionAddress(), id, key);
        worker.getWorker().send(msg);
    }

    @Override
    public void handleRequestGetCacheSize() {
        logger.info("Frontend handle request get cache size");
        AddressedMessage msg = new MessageGetCacheSize(worker.getOwnAddress(),worker.getCompanionAddress());
        worker.getWorker().send(msg);
    }

    @Override
    public void handleRequestGetCacheHitMiss() {
        AddressedMessage msg = new MessageGetCacheHitMiss(worker.getOwnAddress(),worker.getCompanionAddress());
        worker.getWorker().send(msg);
    }

    @Override
    public void showUser(UserDTO user, int key) {
        StringBuilder serialisedJSON = new StringBuilder("{");
        serialisedJSON.append("\"userName\": ").append(gson.toJson(user.getName())).append(",");
        serialisedJSON.append("\"userAge\": ").append(gson.toJson(user.getAge())).append("}");
        logger.info(serialisedJSON.toString());
        Session session = webSocketSessionMap.remove(key);
        if (session.isOpen()) {
            try {
                logger.info("Session send message to client: show user");
                session.getRemote().sendString(serialisedJSON.toString());
            } catch (IOException e) {
                logger.info("Error send websocket message: {}", e.getMessage());
            }
        }
    }

    @Override
    public void showCacheSize(int cacheSize) {
        logger.info("Frontend response show cache size: {}",cacheSize);
        cacheParameterMap.put(CacheParameter.SIZE,cacheSize);
    }

    @Override
    public void showCacheHitMiss(int cacheHits, int cacheMiss) {
        cacheParameterMap.put(CacheParameter.HIT,cacheHits);
        cacheParameterMap.put(CacheParameter.MISS,cacheMiss);
    }

    public void start(){
        executorService.submit(() -> {
            logger.info("Start frontend user service socket");
            while (worker.isConnected()) {
                AddressedMessage msg = worker.takeUserFrontResponse();
                    ((MessageToFrontUser) msg).exec(this);
                    logger.info("Received message is message to FRONT service user: {}", msg);
                }
        });
    }

    @Override
    public ConcurrentMap<Integer, Session> getWebSocketSessionMap() {
        return webSocketSessionMap;
    }

    @Override
    public EnumMap<CacheParameter, Integer> getCacheParameterMap() {
        return cacheParameterMap;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
