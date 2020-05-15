package com.it_uatech.channel;

import com.it_uatech.app.DBServiceAccountMS;
import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.app.MessageToAccountDB;
import com.it_uatech.app.MessageToUserDB;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Message;
import com.it_uatech.messageSystem.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SocketMessageTransferClient extends SocketAdminMessageTransferClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageTransferClient.class);

    private final DBServiceAccountMS serviceAccount;
    private final DBServiceUserMS serviceUser;

    public SocketMessageTransferClient(String host, int port, DBServiceAccountMS serviceAccount, DBServiceUserMS serviceUser) throws IOException {
        super(host, port, ServiceName.DB);
        this.serviceAccount = serviceAccount;
        this.serviceUser = serviceUser;
    }

    public void start() {
        logger.info("Start DB service socket");
        MessageTransfer worker = getWorker();
        Message responseMessage = null;
        while (worker.getSocket().isConnected()) {
            AddressedMessage msg = (AddressedMessage) worker.take();
            if (msg instanceof MessageToAccountDB) {
                responseMessage = ((MessageToAccountDB) msg).exec(serviceAccount);
            } else if (msg instanceof MessageToUserDB) {
                responseMessage = ((MessageToUserDB) msg).exec(serviceUser);
            }else {
                logger.error("Received message is not a message to DB: {}", msg.getTo());
            }
            if (responseMessage != null) worker.send(responseMessage);
        }
    }
}
