package com.it_uatech.service;

import com.it_uatech.addressedMessages.MessageGetAccountByLogin;
import com.it_uatech.addressedMessages.MessageSaveAccount;
import com.it_uatech.api.FrontendServiceAccount;
import com.it_uatech.app.MessageToFrontAccount;
import com.it_uatech.channel.SocketMessageTransferClient;
import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.AddressedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class FrontendServiceAccountImpl implements FrontendServiceAccount {

    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceUserImpl.class);

    private final ExecutorService executorService;
    private final ConcurrentMap<Integer,AccountDTO> httpRequestMap;

    private final SocketMessageTransferClient worker;

    public FrontendServiceAccountImpl(SocketMessageTransferClient worker) {
        this.worker = worker;
        this.executorService = Executors.newSingleThreadExecutor();
        this.httpRequestMap = new ConcurrentHashMap<>();
    }

    @Override
    public void handleRequestRegisterNewAccount(AccountDTO account) {
        logger.info("Frontend handle request: save new account for login - {}",account.getLogin());
        AddressedMessage msg = new MessageSaveAccount(worker.getOwnAddress(),worker.getCompanionAddress(),account);
        worker.getWorker().send(msg);
    }

    @Override
    public void handleRequestGetAccountByLogin(String login, Integer key) {
        logger.info("Frontend handle request: get account for login - {}",login);
        AddressedMessage msg = new MessageGetAccountByLogin(worker.getOwnAddress(),worker.getCompanionAddress(),login,key);
        worker.getWorker().send(msg);
    }

    @Override
    public void validateAccount(Integer key, AccountDTO account) {
        logger.info("Frontend validate account");
        httpRequestMap.put(key, account);
    }

    public void start(){
        executorService.submit(() -> {
            logger.info("Start frontend account service socket");
            while (worker.isConnected()) {
                AddressedMessage msg = worker.takeAccountFrontResponse();
                ((MessageToFrontAccount) msg).exec(this);
                logger.info("Received message is message to FRONT service account: {}", msg);
            }
        });
    }

    @Override
    public ConcurrentMap<Integer, AccountDTO> getHttpRequestMap() {
        return httpRequestMap;
    }

    @Override
    public boolean isConnected() {
        return worker.isConnected();
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
