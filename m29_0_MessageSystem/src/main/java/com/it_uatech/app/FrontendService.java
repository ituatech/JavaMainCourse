package com.it_uatech.app;

import com.it_uatech.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void handleRequest(String login);

    void addUser(int id, String name);
}

