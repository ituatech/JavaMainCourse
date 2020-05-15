package com.it_uatech.api.service;

import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;

import java.util.List;
import java.util.Optional;


public class DBServiceUserImpl implements DBServiceUser {

    private UserDao dao;

    public DBServiceUserImpl(UserDao dao) {
        this.dao = dao;
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
                return dao.findById(id);
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
                return dao.findByName(name);
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
                return dao.findAll();
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
