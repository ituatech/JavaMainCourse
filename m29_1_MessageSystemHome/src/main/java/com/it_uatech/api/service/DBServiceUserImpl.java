package com.it_uatech.api.service;

import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.MessageSystem;

import java.util.List;
import java.util.Optional;


public class DBServiceUserImpl implements DBServiceUser {

    private UserDao dao;
    private Address address;
    private MessageSystemContext msContext;

    public DBServiceUserImpl(UserDao dao) {
        this.dao = dao;
    }

    public DBServiceUserImpl(UserDao dao, Address address, MessageSystemContext msContext) {
        this.dao = dao;
        this.address = address;
        this.msContext = msContext;
    }

    @Override
    public void init() {
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return msContext.getMessageSystem();
    }

    @Override
    public long save(UserDataSet user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = dao.save(user);
                sessionManager.commitSession();
                return userId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public void save(List<UserDataSet> user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.save(user);
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<UserDataSet> findById(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<UserDataSet> optional = dao.findById(id);
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<UserDataSet> findByName(String name) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<UserDataSet> optional = dao.findByName(name);
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public String getLocalStatus() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.getLocalStatus();
            } catch (Exception e) {
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public List<UserDataSet> findAll() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<UserDataSet> userDataSets = dao.findAll();
                return userDataSets;
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
