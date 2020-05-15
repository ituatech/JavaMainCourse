package com.it_uatech.messageSystem;

import com.it_uatech.administrationMessages.OwnCompanionAddressRequestMessage;
import com.it_uatech.administrationMessages.OwnCompanionAddressResponseMessage;
import com.it_uatech.channel.MessageTransfer;
import com.it_uatech.channel.SocketMessageTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

public final class MessageSystemSocketServer implements MessageSystemSocketServerMBean {

    private static final Logger logger = LoggerFactory.getLogger(MessageSystemSocketServer.class);

    private final int port;
    private static final int THREAD_COUNT = 1;

    private final Map<MessageTransfer, ExecutorService> workers;
    private final Map<Address, MessageTransfer> addressWorkers;
    private final BlockingQueue<Address> addressDbQueue;

    public MessageSystemSocketServer() {
        this.workers = new HashMap<>();
        this.addressWorkers = new ConcurrentHashMap<>();
        this.addressDbQueue = new LinkedBlockingQueue<>();
        this.port = initialServerPort();
    }

    private int initialServerPort() {
        String propFileName = "application.properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                logger.error("property file {} not found in the classpath", propFileName);
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            return Integer.parseInt(prop.getProperty("server.port"));
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void start() {
        logger.info("Socket server started on port: {}. Waiting for connections!",port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            do {
                Socket socket = serverSocket.accept();
                SocketMessageTransfer worker = new SocketMessageTransfer(socket);
                ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
                executorService.submit(new MessageWorker(worker, executorService));
            } while (!serverSocket.isClosed());
        } catch (IOException e) {
            logger.error("Server socket error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            workers.forEach((messageTransfer, executor) -> messageTransfer.close());
            workers.clear();
        }
    }

    private class MessageWorker implements Runnable {
        private SocketMessageTransfer worker;
        private Address registeredWorkerAddress;

        public MessageWorker(SocketMessageTransfer worker, ExecutorService executorService) {
            this.worker = worker;
            workers.put(worker, executorService);
            worker.addDisposeTask(() -> {
                workers.get(worker).shutdown();
                workers.remove(worker);
                if (registeredWorkerAddress != null) {
                    addressWorkers.remove(registeredWorkerAddress);
                    addressDbQueue.remove(registeredWorkerAddress);
                }
            });
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                final Message message = worker.take();
                if (message instanceof OwnCompanionAddressRequestMessage) {
                    OwnCompanionAddressRequestMessage msg = (OwnCompanionAddressRequestMessage) message;
                    logger.info("Received register message: {}", msg.toString());
                    handleAdministrationMessage(msg);
                } else if (message instanceof AddressedMessage) {
                    AddressedMessage msg = (AddressedMessage) message;
                    logger.info("Received addressed redirect message from: {} --> to: {}", msg.getFrom(), msg.getTo());
                    handleAddressedMessage(msg);
                } else {
                    logger.error("Message type is incorrect: {}", message.getClass());
                }
            }
        }

        private void handleAdministrationMessage(OwnCompanionAddressRequestMessage msg) {
            ServiceName serviceName = msg.getPrefixName();

            if (!msg.isRequestCompanion()) {
                registeredWorkerAddress = new Address(serviceName);
                if (serviceName == ServiceName.DB) addressDbQueue.add(registeredWorkerAddress);
                addressWorkers.put(registeredWorkerAddress, worker);
                worker.send(new OwnCompanionAddressResponseMessage(registeredWorkerAddress));
            } else {
                if (serviceName == ServiceName.FRONT) {
                    logger.info("Received companion request message: {}", msg.toString());

                    Address address = addressDbQueue.poll();
                    Message responseMessage;
                    if (address != null) {
                        responseMessage = new OwnCompanionAddressResponseMessage(address);
                    } else {
                        responseMessage = new OwnCompanionAddressResponseMessage(null);
                        logger.info("Companion request: no DB services in queue");
                    }
                    worker.send(responseMessage);
                } else if (serviceName == ServiceName.DB) {
                    logger.info("Received companion request message: {}", msg.toString());
                    worker.send(new OwnCompanionAddressResponseMessage(new Address(ServiceName.DB)));
                }

            }
        }


        private void handleAddressedMessage(AddressedMessage msg) {
            if (addressWorkers.containsKey(msg.getTo())) {
                addressWorkers.get(msg.getTo()).send(msg);
            } else {
                logger.error("Companion address does not exist: {}", msg.getTo());
            }
        }
    }
}


