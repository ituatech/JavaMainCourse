package com.it_uatech.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MessageSystem {

    private  static final Logger logger = LoggerFactory.getLogger(MessageSystem.class);

    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<Addressee, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem() {
        this.workers = new ArrayList<>();
        this.messagesMap = new HashMap<>();
        this.addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        messagesMap.putIfAbsent(addressee, new ConcurrentLinkedQueue<>());
        addresseeMap.putIfAbsent(addressee.getAddress(), addressee);
    }

    public void sendMessage(Message message) {
        messagesMap.get(addresseeMap.get(message.getTo())).add(message);
    }

    public void start() {
        for (Map.Entry<Addressee, ConcurrentLinkedQueue<Message>> entry : messagesMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getAddress().getId();
            Thread thread = new Thread(() -> {
               while (true) {
                   ConcurrentLinkedQueue<Message> messagesQueue = entry.getValue();
                   while (!messagesQueue.isEmpty()){
                        Message msg = messagesQueue.poll();
                        msg.exec(entry.getKey());
                   }

                   try {
                       Thread.sleep(DEFAULT_STEP_TIME);
                   } catch (InterruptedException e) {
                       logger.info("Thread interrupted. Finishing: {}. Cause: {}" ,name, e.getCause());
                       return;
                   }
                   if (Thread.currentThread().isInterrupted()){
                       logger.info("Finishing: {}",name);
                        return;
                   }
               }
            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
            logger.info("Thread started: {}",thread.getName());
        }
    }

    public void dispose(){
        workers.forEach(Thread::interrupt);
        addresseeMap.forEach((address,addressee)->addressee.shutdown());
    }

    public Map<Address, Addressee> getAddresseeMap() {
        return addresseeMap;
    }
}

