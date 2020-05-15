package com.it_uatech.channel;

import com.it_uatech.administrationMessages.OwnCompanionAddressRequestMessage;
import com.it_uatech.administrationMessages.OwnCompanionAddressResponseMessage;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class SocketAdminMessageTransferClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketAdminMessageTransferClient.class);

    private final MessageTransfer worker;
    private final Address ownAddress;
    private final Address companionAddress;

    protected SocketAdminMessageTransferClient(String host, int port, ServiceName serviceName) throws IOException {
        this.worker = new SocketMessageTransfer(new Socket(host, port));
        this.ownAddress = register(serviceName);
        this.companionAddress = requestCompanion(serviceName);
    }

    private Address register(ServiceName serviceName) {
        worker.send(new OwnCompanionAddressRequestMessage(serviceName,false));
        OwnCompanionAddressResponseMessage msg = (OwnCompanionAddressResponseMessage) worker.take();
        logger.info("Got own address from server: {}", msg.getAddress());
        return msg.getAddress();
    }

    private Address requestCompanion(ServiceName serviceName) {
        OwnCompanionAddressResponseMessage msg;
        Address address = null;
        do {
            worker.send(new OwnCompanionAddressRequestMessage(serviceName,true));
            msg = (OwnCompanionAddressResponseMessage) worker.take();
            address = msg.getAddress();

            if (address == null) {
                logger.info("Companion address not found");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (address == null);
        logger.info("Got companion address from server: {}", address);
        return address;
    }

    public Address getOwnAddress() {
        return ownAddress;
    }

    public Address getCompanionAddress() {
        return companionAddress;
    }

    public MessageTransfer getWorker() {
        return worker;
    }

    public void close() {
        worker.close();
    }
}
