package com.it_uatech.api.service;

import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount {

    private AccountDao dao;

    public DBServiceAccountImpl(AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(AccountDataSet account) {
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
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<AccountDataSet> optional = dao.findByLogin(login);
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public void shutdown() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.shutdown();
        }
    }
}
