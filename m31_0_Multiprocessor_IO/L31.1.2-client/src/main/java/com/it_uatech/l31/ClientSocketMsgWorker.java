package com.it_uatech.l31;

import com.it_uatech.l31.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tully.
 */
class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;
    private String name;
    private int id;

    ClientSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ClientSocketMsgWorker(Socket socket) {
        super(socket);
        this.socket = socket;
        this.name = "Client-"+ (int) (Math.random()*100 + 1);
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }

    public String getName() {
        return name;
    }
}
