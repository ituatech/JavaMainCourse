package com.it_uatech.api.service;

import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.app.DBServiceAccountMS;
import com.it_uatech.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount, DBServiceAccountMS {

    private static final Logger logger = LoggerFactory.getLogger(DBCachedServiceUserImpl.class);

    private AccountDao dao;

    public DBServiceAccountImpl(AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(AccountDataSet account) {
        logger.info("Save account: {}",account.getLogin());
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.save(account);
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<AccountDataSet> findByLogin(String login) {
        logger.info("Find account: {}",login);
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.findByLogin(login);
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public void saveAccount(AccountDTO account) {
        save(new AccountDataSet(account.getLogin(),account.getPassword()));
    }

    @Override
    public Optional<AccountDTO> findAccountByLogin(String login) {
        Optional<AccountDataSet> optional = findByLogin(login);
        return optional.map(accountDataSet -> new AccountDTO(accountDataSet.getLogin(), accountDataSet.getPassword()));
    }

    @Override
    public void shutdown() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.shutdown();
        }
    }
}
