package com.it_uatech.messageSystem;

public interface Addressee {
    Address getAddress();

    MessageSystem getMessageSystem();

    void shutdown();
}
