package com.it_uatech.channel;

import com.it_uatech.messageSystem.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageTransfer implements MessageTransfer{

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageTransfer.class);
    private static final int CHANNEL_COUNT = 2;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    private final ExecutorService executorService;
    private final Socket socket;

    private Runnable disposeTask;

    public SocketMessageTransfer(Socket socket) {
        this.socket = socket;
        this.executorService = Executors.newFixedThreadPool(CHANNEL_COUNT);
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }

    public void addDisposeTask(Runnable disposeTask){
        this.disposeTask = disposeTask;
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public Message take() {
        try {
            return input.take();
        } catch (InterruptedException e) {
            logger.error("Socket take message exception: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message peek() {
        return input.peek();
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void close() {
        if (disposeTask != null) disposeTask.run();
        executorService.shutdown();
        try {
            socket.close();
        } catch (IOException e) {
            logger.error("Socket close exception: ", e);
        }
    }

    private void sendMessage() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            while (socket.isConnected()){
                final Message msg = output.take();
                oos.writeObject(msg);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error sending an Object: ", e);
        }
    }

    private void receiveMessage() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())){
            while (socket.isConnected()){
                final Message msg = (Message) ois.readObject();
                input.add(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error receiving an Object: ", e);
        } finally {
            close();
        }
    }
}
