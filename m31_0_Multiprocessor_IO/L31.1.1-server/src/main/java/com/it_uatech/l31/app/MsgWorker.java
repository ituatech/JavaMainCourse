package com.it_uatech.l31.app;

import com.it_uatech.l31.channel.Blocks;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException;
}
