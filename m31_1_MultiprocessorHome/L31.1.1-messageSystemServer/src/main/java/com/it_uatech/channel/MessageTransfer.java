package com.it_uatech.channel;

import com.it_uatech.messageSystem.Message;

import java.net.Socket;


public interface MessageTransfer {

    void send(Message message);

    Message poll();

    Message take();

    Message peek();

    Socket getSocket();

    void close();
}
