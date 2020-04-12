package com.it_uatech.api.service;

import com.google.gson.Gson;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.messages.MessageGetUserById;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Message;
import com.it_uatech.messageSystem.MessageSystem;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FrontendServiceImpl implements FrontendService {

    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    private final Address address;
    private final MessageSystemContext msContext;
    private final Gson gson;

    public FrontendServiceImpl(Address address, MessageSystemContext msContext, Gson gson) {
        this.address = address;
        this.msContext = msContext;
        this.gson = gson;
    }

    @Override
    public void init() {
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public void handleRequest(Long id, Session session) {
        logger.info("Frontend handle request");
        Message msg = new MessageGetUserById(msContext.getFrontAddress(),msContext.getDbAddress(),id, session);
        getMessageSystem().sendMessage(msg);
    }

    @Override
    public void showUser(UserDataSet uds, Session session) {
        StringBuilder serialisedJSON = new StringBuilder("{");
        serialisedJSON.append("\"userName\": ").append(gson.toJson(uds.getName())).append(",");
        serialisedJSON.append("\"userAge\": ").append(gson.toJson(uds.getAge())).append("}");
        logger.info(serialisedJSON.toString());
        if (session.isOpen()) {
            try {
                logger.info("Session send message to client");
                session.getRemote().sendString(serialisedJSON.toString());
            } catch (IOException e) {
                logger.info("Error send websocket message: {}",e.getMessage());
            }
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return msContext.getMessageSystem();
    }

    @Override
    public void shutdown() {

    }

}
