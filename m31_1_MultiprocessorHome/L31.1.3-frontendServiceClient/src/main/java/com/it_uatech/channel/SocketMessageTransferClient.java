package com.it_uatech.channel;

import com.it_uatech.api.FrontendResponseMessage;
import com.it_uatech.app.MessageToFrontAccount;
import com.it_uatech.app.MessageToFrontUser;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Message;
import com.it_uatech.messageSystem.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageTransferClient extends SocketAdminMessageTransferClient implements FrontendResponseMessage {

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageTransferClient.class);

    private final ExecutorService executorService;

    private final BlockingQueue<Message> userServiceResponseQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> accountServiceResponseQueue = new LinkedBlockingQueue<>();

    public SocketMessageTransferClient(String host, int port) throws IOException {
        super(host, port, ServiceName.FRONT);
        this.executorService = Executors.newSingleThreadExecutor();
        checkResponseMessages();
    }

    private void checkResponseMessages (){
        executorService.submit(() -> {
            logger.info("Checking frontend response messages started");
            MessageTransfer messageTransfer = getWorker();
            while (messageTransfer.getSocket().isConnected()) {
                AddressedMessage msg = (AddressedMessage) messageTransfer.take();
                if (msg instanceof MessageToFrontAccount) {
                    accountServiceResponseQueue.add(msg);
                } else if (msg instanceof MessageToFrontUser) {
                    userServiceResponseQueue.add(msg);
                } else {
                    logger.error("Received message is not a message to FRONT: {}", msg.getTo());
                }
            }
        });
    }

    @Override
    public AddressedMessage takeAccountFrontResponse(){
        try {
            return (AddressedMessage) accountServiceResponseQueue.take();
        } catch (InterruptedException e) {
            logger.error("Socket take account front message exception: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public AddressedMessage takeUserFrontResponse(){
        try {
            return (AddressedMessage) userServiceResponseQueue.take();
        } catch (InterruptedException e) {
            logger.error("Socket take user front message exception: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return getWorker().getSocket().isConnected();
    }

    @Override
    public void close() {
        super.close();
        executorService.shutdown();
    }
}
