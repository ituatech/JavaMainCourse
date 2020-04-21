package com.it_uatech.api.service;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.messageSystem.Addressee;
import org.eclipse.jetty.websocket.api.Session;

public interface FrontendService extends Addressee {

    void init();

    void handleRequest(Long id, Session session);

    void showUser(UserDataSet uds, Session session);

    void shutdown();
}
