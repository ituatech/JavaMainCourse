package com.it_uatech.api.service;

import com.it_uatech.api.cache.CacheElement;
import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;

import java.util.List;
import java.util.Optional;

public class DBCachedServiceUserImpl implements DBServiceUser{
    private UserDao dao;
    private CacheEngine<Long,UserDataSet> cacheEngine;

    public DBCachedServiceUserImpl(UserDao dao, CacheEngine cache) {
        this.dao = dao;
        this.cacheEngine = cache;
    }

    @Override
    public long save(UserDataSet user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = dao.save(user);
                sessionManager.commitSession();
                cacheEngine.put(new CacheElement(userId,user));
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
                user.forEach(person ->{
                    long personId = dao.save(person);
                    cacheEngine.put(new CacheElement<>(personId,person));
                });
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<UserDataSet> findById(long id) {
        UserDataSet uds = cacheEngine.get(id).getValue();
        if (uds != null){
            return Optional.of(uds);
        }
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
                if(optional.isPresent()) {
                    cacheEngine.put(new CacheElement(optional.get().getId(), optional.get()));
                }
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public String getLocalStatus() {
        return "Cache hits: " + cacheEngine.getHitCount()
                +", cache miss: " + cacheEngine.getMissCount();
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
            cacheEngine.dispose();
        }
    }

}
